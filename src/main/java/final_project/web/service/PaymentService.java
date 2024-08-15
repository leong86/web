package final_project.web.service;

import final_project.web.entity.Payment;
import final_project.web.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

    private final Map<String, Object> locks = new ConcurrentHashMap<>();

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment submitPayment(Payment payment) {
        String transactionId = payment.getTransactionId();
        Object lock = locks.computeIfAbsent(transactionId, k -> new Object());

        synchronized (lock) {
            try {
                // Check if a payment with the same transactionId already exists
                Optional<Payment> existingPayment = paymentRepository.findByTransactionId(transactionId);

                if (existingPayment.isPresent()) {
                    // Return the existing payment to ensure idempotency
                    return existingPayment.get();
                }

                // If not, proceed to save the new payment
                payment.setStatus("COMPLETED");
                Payment savedPayment = paymentRepository.save(payment);

                return savedPayment;
            } finally {
                // Remove the lock after processing
                locks.remove(transactionId);
            }
        }
    }

    public Payment updatePayment(UUID paymentId, Payment paymentDetails) {
        return paymentRepository.findById(paymentId).map(payment -> {
            payment.setAmount(paymentDetails.getAmount());
            payment.setPaymentMethod(paymentDetails.getPaymentMethod());
            payment.setStatus(paymentDetails.getStatus());
            return paymentRepository.save(payment);
        }).orElse(null);
    }

    public boolean reversePayment(UUID paymentId) {
        return paymentRepository.findById(paymentId).map(payment -> {
            if (!"COMPLETED".equals(payment.getStatus())) {
                throw new IllegalStateException("Only completed payments can be refunded.");
            }
            payment.setStatus("REFUNDED");
            paymentRepository.save(payment);
            return true;
        }).orElse(false);
    }

    public Optional<Payment> lookupPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}

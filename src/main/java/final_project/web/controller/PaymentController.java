package final_project.web.controller;

import final_project.web.entity.Payment;
import final_project.web.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> submitPayment(@RequestBody Payment payment) {
        Payment submittedPayment = paymentService.submitPayment(payment);
        return ResponseEntity.ok(submittedPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable UUID id, @RequestBody Payment paymentDetails) {
        Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
        return updatedPayment != null ? ResponseEntity.ok(updatedPayment) : ResponseEntity.notFound().build();
    }

    @PostMapping("/refund/{id}")
    public ResponseEntity<String> reversePayment(@PathVariable UUID id) {
        boolean isRefunded = paymentService.reversePayment(id);
        return isRefunded ? ResponseEntity.ok("Payment refunded.") : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> lookupPayment(@PathVariable UUID id) {
        Optional<Payment> payment = paymentService.lookupPayment(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}
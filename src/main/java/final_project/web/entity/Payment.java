package final_project.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    private UUID paymentId;
    private UUID orderId;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;
    private String transactionId;

    public Payment() {
        // Initialize paymentId when the entity is created
        this.paymentId = UUID.randomUUID();
    }

    @PrePersist
    public void prePersist() {
        // Ensure paymentId is generated before persisting
        if (this.paymentId == null) {
            this.paymentId = UUID.randomUUID();
        }
    }

    // Getters and Setters

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

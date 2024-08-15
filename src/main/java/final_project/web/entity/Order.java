package final_project.web.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    private UUID userId;
    private UUID itemId;
    private String orderStatus;
    private Instant orderDate;
    private Instant updateDate;
    private int quantity;
    private BigDecimal totalPrice;
    private UUID paymentId;
    private String shippingAddress;

    // Constructors
    public Order() {
        this.orderId = UUID.randomUUID();
        this.orderDate = Instant.now();
        this.updateDate = Instant.now();
    }

    public Order(UUID userId, UUID itemId, String orderStatus, int quantity, BigDecimal totalPrice, UUID paymentId, String shippingAddress) {
        this();
        this.userId = userId;
        this.itemId = itemId;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentId = paymentId;
        this.shippingAddress = shippingAddress;
    }

    // Getters and Setters
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

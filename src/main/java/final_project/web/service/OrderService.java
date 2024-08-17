package final_project.web.service;

import final_project.web.entity.Order;
import final_project.web.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.websocket.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    private static final String TOPIC = "order-topic";

    @Transactional
    public Order createOrder(Order order) {
        try {
            order.setCreationTimestamp(LocalDateTime.now());
            if (order.getOrderStatus() == null) {
                order.setOrderStatus("Created");
            }
            Order savedOrder = orderRepository.save(order);
            kafkaTemplate.send(TOPIC, "create-order", savedOrder);
            return savedOrder;
        } catch (Exception ex) {
            throw ex; // Re-throw to understand the issue better
        }
    }



    public Optional<Order> getOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(UUID orderId, Order orderDetails) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    // Only update fields that are not null in the orderDetails
                    if (orderDetails.getItemId() != null) {
                        order.setItemId(orderDetails.getItemId());
                    }
                    if (orderDetails.getOrderStatus() != null) {
                        order.setOrderStatus(orderDetails.getOrderStatus());
                    }
                    if (orderDetails.getQuantity() != 0) {  // Assuming quantity should not be zero
                        order.setQuantity(orderDetails.getQuantity());
                    }
                    if (orderDetails.getTotalPrice() != null) {
                        order.setTotalPrice(orderDetails.getTotalPrice());
                    }
                    if (orderDetails.getPaymentId() != null) {
                        order.setPaymentId(orderDetails.getPaymentId());
                    }
                    if (orderDetails.getShippingAddress() != null) {
                        order.setShippingAddress(orderDetails.getShippingAddress());
                    }

                    // Always update the updateDate
                    order.setUpdateDate(Instant.now());

                    // Save the updated order
                    Order updatedOrder = orderRepository.save(order);

                    // Send the updated order to Kafka
                    kafkaTemplate.send(TOPIC, "update-order", updatedOrder);

                    return updatedOrder;
                })
                .orElse(null);  // Return null if order is not found
    }



    @Transactional
    public boolean deleteOrder(UUID orderId) {
        return orderRepository.findById(orderId).map(order -> {
            orderRepository.delete(order);
            kafkaTemplate.send(TOPIC, "delete-order", order);
            return true;
        }).orElse(false);
    }


}
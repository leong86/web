package final_project.web.service;

import final_project.web.entity.Order;
import final_project.web.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

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

    public Order createOrder(Order order) {
        // Set the current time as the creation timestamp
        order.setCreationTimestamp(LocalDateTime.now());

        // Set default status if necessary
        if (order.getOrderStatus() == null) {
            order.setOrderStatus("Created");
        }

        // Save order to repository
        return orderRepository.save(order);
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
                    order.setItemId(orderDetails.getItemId());
                    order.setOrderStatus(orderDetails.getOrderStatus());
                    order.setQuantity(orderDetails.getQuantity());
                    order.setTotalPrice(orderDetails.getTotalPrice());
                    order.setPaymentId(orderDetails.getPaymentId());
                    order.setShippingAddress(orderDetails.getShippingAddress());
                    order.setUpdateDate(Instant.from(LocalDateTime.now()));
                    Order updatedOrder = orderRepository.save(order);
                    kafkaTemplate.send(TOPIC, "update-order", updatedOrder);
                    return updatedOrder;
                })
                .orElse(null);
    }

    public boolean deleteOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    orderRepository.delete(order);
                    kafkaTemplate.send(TOPIC, "cancel-order", order);
                    return true;
                })
                .orElse(false);
    }
}
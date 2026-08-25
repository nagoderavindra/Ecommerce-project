package com.ravindra.kafka;

import com.ravindra.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group"
    )
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {

        System.out.println("====================================");
        System.out.println("       ORDER CREATED EVENT");
        System.out.println("====================================");

        System.out.println("Order ID     : " + event.getOrderId());
        System.out.println("User ID      : " + event.getUserId());
        System.out.println("Product ID   : " + event.getProductId());
        System.out.println("Product Name : " + event.getProductName());
        System.out.println("Quantity     : " + event.getQuantity());
        System.out.println("Total Price  : " + event.getTotalPrice());
        System.out.println("Status       : " + event.getStatus());
        System.out.println("Order Date   : " + event.getOrderDate());

        System.out.println("====================================");
    }
}
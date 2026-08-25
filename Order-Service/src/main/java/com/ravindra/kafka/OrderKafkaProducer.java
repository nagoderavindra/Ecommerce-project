package com.ravindra.kafka;

import com.ravindra.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaProducer {

    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderKafkaProducer(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getOrderId().toString(),
                event
        ).whenComplete((result, ex) -> {

            if (ex != null) {
                System.out.println(
                        "KAFKA SEND FAILED: orderId="
                                + event.getOrderId()
                                + " error=" + ex.getMessage()
                );
            } else {
                System.out.println(
                        "KAFKA SEND SUCCESS: orderId="
                                + event.getOrderId()
                                + " partition="
                                + result.getRecordMetadata().partition()
                                + " offset="
                                + result.getRecordMetadata().offset()
                );
            }
        });
    }
}
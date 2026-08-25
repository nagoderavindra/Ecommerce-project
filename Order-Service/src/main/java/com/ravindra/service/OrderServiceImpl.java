package com.ravindra.service;

import com.ravindra.dto.OrderRequestDTO;
import com.ravindra.dto.OrderResponseDTO;
import com.ravindra.dto.ProductResponseDTO;
import com.ravindra.dto.UserResponse;
import com.ravindra.entity.Order;
import com.ravindra.entity.OrderStatus;
import com.ravindra.event.OrderCreatedEvent;
import com.ravindra.external.ProductServiceClient;
import com.ravindra.external.UserServiceClient;
import com.ravindra.kafka.OrderKafkaProducer;
import com.ravindra.repository.OrderRepository;
import com.ravindra.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    private final UserServiceClient userServiceClient;
    private final OrderKafkaProducer orderKafkaProducer;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductServiceClient productServiceClient,
                            ModelMapper modelMapper,
                            JwtService jwtService,
                            UserServiceClient userServiceClient,
                            OrderKafkaProducer orderKafkaProducer) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.userServiceClient = userServiceClient;
        this.orderKafkaProducer = orderKafkaProducer;
    }


    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO request, String authHeader) {

        String token = authHeader.substring(7);

        String email = jwtService.extractUsername(token);

        UserResponse user = userServiceClient.getUserByEmail(email);

        ProductResponseDTO product =
                productServiceClient.getProductById(request.getProductId());

        Order order = new Order();

        order.setUserId(user.getId());
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal totalPrice =
                BigDecimal.valueOf(product.getPrice())
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        // 8. Create Kafka Event
        OrderCreatedEvent event =
                new OrderCreatedEvent(
                        savedOrder.getId(),
                        savedOrder.getUserId(),
                        savedOrder.getProductId(),
                        product.getName(),
                        savedOrder.getQuantity(),
                        savedOrder.getTotalPrice(),
                        savedOrder.getStatus().name(),
                        savedOrder.getOrderDate()
                );

        // 9. Publish event to Kafka
        orderKafkaProducer.sendOrderCreatedEvent(event);


        OrderResponseDTO response = new OrderResponseDTO();

        response.setOrderId(savedOrder.getId());
        response.setUserId(savedOrder.getUserId());
        response.setProductId(savedOrder.getProductId());
        response.setProductName(product.getName());
        response.setPrice(BigDecimal.valueOf(product.getPrice()));
        response.setQuantity(savedOrder.getQuantity());
        response.setTotalPrice(savedOrder.getTotalPrice());
        response.setStatus(savedOrder.getStatus());
        response.setOrderDate(savedOrder.getOrderDate());

        return response;
    }
}
package com.ravindra.controller;

import com.ravindra.dto.OrderRequestDTO;
import com.ravindra.dto.OrderResponseDTO;
import com.ravindra.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }



    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody OrderRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(request, authHeader));
    }
}

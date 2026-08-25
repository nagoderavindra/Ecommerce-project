package com.ravindra.service;

import com.ravindra.dto.OrderRequestDTO;
import com.ravindra.dto.OrderResponseDTO;

public interface OrderService {


    public OrderResponseDTO placeOrder(
            OrderRequestDTO request,
            String authHeader);

}
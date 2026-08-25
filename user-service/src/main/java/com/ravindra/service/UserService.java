package com.ravindra.service;

import com.ravindra.dto.LoginRequest;
import com.ravindra.dto.LoginResponse;
import com.ravindra.dto.UserRequest;
import com.ravindra.dto.UserResponse;

public interface UserService {

     UserResponse register(UserRequest request);
     LoginResponse login(LoginRequest request);
     UserResponse getUserByEmail(String email);
}

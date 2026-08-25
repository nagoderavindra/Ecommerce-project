package com.ravindra.service.Impl;

import com.ravindra.dto.LoginRequest;
import com.ravindra.dto.LoginResponse;
import com.ravindra.dto.UserRequest;
import com.ravindra.dto.UserResponse;
import com.ravindra.entity.User;
import com.ravindra.exception.InvalidCredentialsException;
import com.ravindra.exception.ResourceAlreadyExistsException;
import com.ravindra.exception.ResourceNotFoundException;
import com.ravindra.repository.UserRepository;
import com.ravindra.security.jwt.JwtService;
import com.ravindra.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

     private final UserRepository userRepository;
     private final ModelMapper modelMapper;
     private final PasswordEncoder passwordEncoder;
     private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(UserRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // DTO -> Entity
        User user = modelMapper.map(request, User.class);

           user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Save
        User savedUser = userRepository.save(user);

        // Entity -> DTO
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
         // find by user by email
        User user =  userRepository.findByEmail(request.getEmail()).orElseThrow(() ->

              new ResourceNotFoundException("Invalid Email"));

            if(!passwordEncoder.matches(request.getPassword(),user.getPassword()))
            {
                throw  new InvalidCredentialsException("Invalid password");
         }
                   String Token = jwtService.generateToken(user.getEmail());
            LoginResponse response = new LoginResponse();
            response.setMessage("Login is successfully ");
            response.setToken(Token);

            return response;

    }

    @Override
    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return modelMapper.map(user, UserResponse.class);
    }
}

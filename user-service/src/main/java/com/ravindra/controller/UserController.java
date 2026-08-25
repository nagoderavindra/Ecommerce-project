package com.ravindra.controller;


import com.ravindra.dto.LoginRequest;
import com.ravindra.dto.LoginResponse;
import com.ravindra.dto.UserRequest;
import com.ravindra.dto.UserResponse;
import com.ravindra.entity.User;
import com.ravindra.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {


         private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

       @PostMapping("/register")
    public ResponseEntity <UserResponse> userRagister( @Valid  @RequestBody UserRequest request)
      {
                       UserResponse  response = userService.register(request);

                       return new ResponseEntity<>(response, HttpStatus.CREATED);
      }


        @PostMapping("/login")
              public ResponseEntity<LoginResponse> login(
                      @Valid @RequestBody LoginRequest request)
              {
                     LoginResponse response =  userService.login(request);

                     return ResponseEntity.ok(response);
              }

    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Welcome Ravindra, JWT Authentication Successful");
    }
    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {

        return userService.getUserByEmail(email);

    }
}

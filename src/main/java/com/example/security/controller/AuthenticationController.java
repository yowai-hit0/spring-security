package com.example.security.controller;

import com.example.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.RegisterUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.loginUser(request));
    }
}

package com.example.security.services;


import com.example.security.controller.ApiResponse;
import com.example.security.controller.LoginRequest;
import com.example.security.controller.RegisterRequest;
import com.example.security.repository.UserRepository;
import com.example.security.user.Role;
import com.example.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ApiResponse RegisterUser(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        String jwt_token =  jwtService.generateToken(user);
        return ApiResponse.builder().token(jwt_token).build();
    }

    public ApiResponse loginUser(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepo.FindByEmail(request.getEmail()).orElseThrow();
        var JwtToken = jwtService.generateToken(user);
        return ApiResponse.builder().token(JwtToken).build();
    }
}

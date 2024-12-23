package com.example.security.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse{
    private String token;
}

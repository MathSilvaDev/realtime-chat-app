package com.matheus.realtimechat.auth.controller;

import com.matheus.realtimechat.auth.dto.request.LoginRequest;
import com.matheus.realtimechat.auth.dto.request.RegisterRequest;
import com.matheus.realtimechat.auth.dto.response.LoginResponse;
import com.matheus.realtimechat.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest request){

        authService.register(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }
}

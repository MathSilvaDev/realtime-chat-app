package com.matheus.realtimechat.application.user.controller;

import com.matheus.realtimechat.application.user.dto.response.UserInfoResponse;
import com.matheus.realtimechat.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private UUID getUserIdFromAuthentication(Authentication auth){
        Jwt jwt = (Jwt) auth.getPrincipal();
        return UUID.fromString(jwt.getClaimAsString("userId"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> userInfo(Authentication auth){
        UUID id = getUserIdFromAuthentication(auth);

        return ResponseEntity.ok(userService.userInfo(id));
    }
}

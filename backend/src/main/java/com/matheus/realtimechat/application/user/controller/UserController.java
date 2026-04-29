package com.matheus.realtimechat.application.user.controller;

import com.matheus.realtimechat.application.user.dto.response.UserInfoResponse;
import com.matheus.realtimechat.application.user.service.UserService;
import com.matheus.realtimechat.common.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> userInfo(@AuthenticationPrincipal Jwt jwt){
        UUID id = AuthUtils.getUserId(jwt);

        return ResponseEntity.ok(userService.userInfo(id));
    }
}

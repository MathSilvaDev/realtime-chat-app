package com.matheus.realtimechat.application.auth.dto.response;

public record LoginResponse(
        String token,
        Long expiresAt
) { }

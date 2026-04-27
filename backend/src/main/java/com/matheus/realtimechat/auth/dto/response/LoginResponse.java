package com.matheus.realtimechat.auth.dto.response;

public record LoginResponse(
        String token,
        Long expiresAt
) { }

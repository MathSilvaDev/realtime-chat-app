package com.matheus.realtimechat.infrastructure.security.jwt.dto;

public record TokenData(
        String token,
        Long expiresAt
) { }

package com.matheus.realtimechat.infrastructure.security.jwt.dto;

import java.util.UUID;

public record TokenData(
        String token,
        Long expiresAt,
        UUID userId
) { }

package com.matheus.realtimechat.application.auth.dto.response;

import java.util.UUID;

public record LoginResponse(
        String token,
        Long expiresAt,
        UUID userId
) { }

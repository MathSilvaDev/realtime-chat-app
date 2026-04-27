package com.matheus.realtimechat.application.user.dto.response;

import java.time.Instant;

public record UserInfoResponse(
        String username,
        String handleName,
        Instant createdAt
) { }

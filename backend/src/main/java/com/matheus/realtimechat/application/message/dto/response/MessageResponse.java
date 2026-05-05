package com.matheus.realtimechat.application.message.dto.response;

import java.util.UUID;

public record MessageResponse(
        String content,
        UUID senderId
) {}

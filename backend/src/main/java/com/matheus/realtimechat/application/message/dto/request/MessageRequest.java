package com.matheus.realtimechat.application.message.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record MessageRequest(
        @NotBlank
        UUID contactId,

        @NotBlank
        String message
) { }

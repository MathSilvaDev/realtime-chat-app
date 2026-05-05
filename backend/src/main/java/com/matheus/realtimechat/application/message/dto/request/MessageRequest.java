package com.matheus.realtimechat.application.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MessageRequest(
        @NotNull
        UUID contactId,

        @NotBlank
        String message
) { }

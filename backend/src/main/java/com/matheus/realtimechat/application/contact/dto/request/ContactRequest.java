package com.matheus.realtimechat.application.contact.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ContactRequest(
        @NotBlank
        String username
) { }

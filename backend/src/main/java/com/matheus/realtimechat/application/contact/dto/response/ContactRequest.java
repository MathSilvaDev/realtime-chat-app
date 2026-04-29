package com.matheus.realtimechat.application.contact.dto.response;

import jakarta.validation.constraints.NotBlank;

public record ContactRequest(
        @NotBlank
        String username
) { }

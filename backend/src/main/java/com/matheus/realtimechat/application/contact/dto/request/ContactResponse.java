package com.matheus.realtimechat.application.contact.dto.request;

import java.util.UUID;

public record ContactResponse(
        UUID id,
        String name,
        String username
) {}

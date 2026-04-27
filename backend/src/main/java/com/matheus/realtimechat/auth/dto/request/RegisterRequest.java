package com.matheus.realtimechat.auth.dto.request;

import com.matheus.realtimechat.validation.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank
        @Size(min = 3, max = ValidationConstants.USERNAME_MAX_LENGTH)
        String username,

        @NotBlank
        @Size(min = ValidationConstants.PASSWORD_MIN_LENGTH)
        String password

) { }

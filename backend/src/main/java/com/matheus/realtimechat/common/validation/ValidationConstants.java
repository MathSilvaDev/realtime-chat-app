package com.matheus.realtimechat.common.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstants {

    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 8;
}

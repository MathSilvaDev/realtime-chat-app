package com.matheus.realtimechat.common.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {

    public static UUID getUserId(Jwt jwt){
        return UUID.fromString(jwt.getClaimAsString("userId"));
    }
}

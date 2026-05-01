package com.matheus.realtimechat.infrastructure.security.jwt;

import com.matheus.realtimechat.domain.user.entity.User;
import com.matheus.realtimechat.infrastructure.security.jwt.dto.TokenData;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class JwtService {

    private static final long EXPIRES_AT = 60L * 60L * 24L;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public TokenData generateToken(User user){

        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("BACKEND_JAVA(MatheusSilva)")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRES_AT))
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .claim("roles",
                        user.getRoles().stream()
                                .map(role -> "ROLE_" + role.getName())
                                .toList()
                )
                .build();

        String token = jwtEncoder
                .encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();

        return new TokenData(token, EXPIRES_AT, user.getId());
    }

    public TokenData validateToken(String token){

        Jwt jwt = jwtDecoder.decode(token);

        String userIdStr = jwt.getClaim("userId");

        return new TokenData(
                token,
                jwt.getExpiresAt().getEpochSecond(),
                UUID.fromString(userIdStr)
        );
    }
}

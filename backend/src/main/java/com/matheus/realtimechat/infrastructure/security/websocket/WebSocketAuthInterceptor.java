package com.matheus.realtimechat.infrastructure.security.websocket;

import com.matheus.realtimechat.infrastructure.security.jwt.JwtService;
import com.matheus.realtimechat.infrastructure.security.jwt.dto.TokenData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final WebSocketSessionRegistry sessionRegistry;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String token = resolveToken(accessor);

            if (token == null) {
                throw new RuntimeException("Token Notfound");
            }

            TokenData tokenData = jwtService.validateToken(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            tokenData,
                            null,
                            List.of()
                    );

            if (sessionId != null) {
                sessionRegistry.register(sessionId, authentication);
            }

            accessor.setUser(authentication);
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            if (sessionId != null) {
                sessionRegistry.remove(sessionId);
            }
        } else if (sessionId != null && accessor.getUser() == null) {
            accessor.setUser(sessionRegistry.getAuthentication(sessionId));
        }

        return message;
    }

    private String resolveToken(StompHeaderAccessor accessor) {
        String authorization = accessor.getFirstNativeHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        if (accessor.getSessionAttributes() == null) {
            return null;
        }

        return (String) accessor.getSessionAttributes().get("token");
    }
}

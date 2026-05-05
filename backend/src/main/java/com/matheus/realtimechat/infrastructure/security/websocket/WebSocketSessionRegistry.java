package com.matheus.realtimechat.infrastructure.security.websocket;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {

    private final Map<String, UsernamePasswordAuthenticationToken> authentications = new ConcurrentHashMap<>();

    public void register(String sessionId, UsernamePasswordAuthenticationToken authentication) {
        authentications.put(sessionId, authentication);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String sessionId) {
        return authentications.get(sessionId);
    }

    public void remove(String sessionId) {
        authentications.remove(sessionId);
    }
}

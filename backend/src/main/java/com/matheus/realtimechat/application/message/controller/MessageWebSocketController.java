package com.matheus.realtimechat.application.message.controller;

import com.matheus.realtimechat.application.message.dto.request.MessageRequest;
import com.matheus.realtimechat.application.message.dto.response.MessageResponse;
import com.matheus.realtimechat.application.message.service.MessageService;
import com.matheus.realtimechat.infrastructure.security.jwt.dto.TokenData;
import com.matheus.realtimechat.infrastructure.security.websocket.WebSocketSessionRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionRegistry sessionRegistry;

    @MessageMapping("/chat.send")
    public void sendMessage(Principal principal,
                            @Header("simpSessionId") String sessionId,
                            @Valid MessageRequest request){

        UsernamePasswordAuthenticationToken authentication = resolveAuthentication(principal, sessionId);

        if (authentication == null) {
            throw new RuntimeException("User Not Found");
        }

        TokenData tokenData = (TokenData) authentication.getPrincipal();

        UUID userId = tokenData.userId();

        MessageResponse response = messageService.sendMessage(userId, request);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + getChatId(userId, request.contactId()),
                response
        );
    }

    private UsernamePasswordAuthenticationToken resolveAuthentication(Principal principal, String sessionId) {
        if (principal instanceof UsernamePasswordAuthenticationToken authentication) {
            return authentication;
        }

        return sessionRegistry.getAuthentication(sessionId);
    }

    private String getChatId(UUID firstUserId, UUID secondUserId) {
        String first = firstUserId.toString();
        String second = secondUserId.toString();

        return first.compareTo(second) < 0
                ? first + "_" + second
                : second + "_" + first;
    }
}

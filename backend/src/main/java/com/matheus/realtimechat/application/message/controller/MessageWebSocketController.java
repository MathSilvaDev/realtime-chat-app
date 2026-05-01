package com.matheus.realtimechat.application.message.controller;

import com.matheus.realtimechat.common.security.AuthUtils;
import com.matheus.realtimechat.application.message.dto.request.MessageRequest;
import com.matheus.realtimechat.application.message.dto.response.MessageResponse;
import com.matheus.realtimechat.application.message.service.MessageService;
import com.matheus.realtimechat.infrastructure.security.jwt.dto.TokenData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@AuthenticationPrincipal TokenData tokenData,
                            @Valid MessageRequest request){

        UUID userId = tokenData.userId();

        MessageResponse response = messageService.sendMessage(userId, request);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.contactId(),
                response
        );
    }
}

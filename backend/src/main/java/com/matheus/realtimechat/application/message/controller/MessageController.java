package com.matheus.realtimechat.application.message.controller;

import com.matheus.realtimechat.application.message.dto.response.MessageResponse;
import com.matheus.realtimechat.application.message.service.MessageService;
import com.matheus.realtimechat.common.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/me/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{contactId}")
    public ResponseEntity<List<MessageResponse>> findMessages(
            @AuthenticationPrincipal Jwt jwt, @PathVariable UUID contactId){

        UUID userId = AuthUtils.getUserId(jwt);

        return ResponseEntity.ok(
                messageService.findMessages(userId, contactId)
        );
    }
}

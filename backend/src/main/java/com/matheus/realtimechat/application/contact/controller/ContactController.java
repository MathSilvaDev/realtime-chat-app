package com.matheus.realtimechat.application.contact.controller;

import com.matheus.realtimechat.application.contact.dto.response.ContactResponse;
import com.matheus.realtimechat.application.contact.dto.request.ContactRequest;
import com.matheus.realtimechat.application.contact.service.ContactService;
import com.matheus.realtimechat.common.security.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/me/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactResponse>> findContacts(@AuthenticationPrincipal Jwt jwt){
        UUID userId = AuthUtils.getUserId(jwt);

        return ResponseEntity.ok(contactService.findContacts(userId));
    }

    @PostMapping
    public ResponseEntity<ContactResponse> addContact(
            @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ContactRequest request){

        UUID userId = AuthUtils.getUserId(jwt);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactService.addContact(userId, request));
    }
}

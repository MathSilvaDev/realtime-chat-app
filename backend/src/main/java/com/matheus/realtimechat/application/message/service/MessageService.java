package com.matheus.realtimechat.application.message.service;

import com.matheus.realtimechat.application.message.dto.request.MessageRequest;
import com.matheus.realtimechat.application.message.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    public MessageResponse sendMessage(UUID userId, MessageRequest request){
        return null;
    }
}

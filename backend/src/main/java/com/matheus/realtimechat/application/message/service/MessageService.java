package com.matheus.realtimechat.application.message.service;

import com.matheus.realtimechat.application.message.dto.request.MessageRequest;
import com.matheus.realtimechat.application.message.dto.response.MessageResponse;
import com.matheus.realtimechat.domain.message.entity.Message;
import com.matheus.realtimechat.domain.message.repository.MessageRepository;
import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
import com.matheus.realtimechat.domain.usercontact.repository.UserContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserContactRepository userContactRepository;

    public MessageResponse sendMessage(UUID userId, MessageRequest request){
        UserContact userContact = userContactRepository
                .findByUser_IdAndContact_Id(userId, request.contactId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "UserContact NotFound"));

        Message message = new Message(request.message(), userContact);

        messageRepository.save(message);

        return toResponse(message);
    }

    private MessageResponse toResponse(Message message){
        return new MessageResponse(
                message.getContent(),
                message.getUserContact().getUser().getId()
        );
    }
}

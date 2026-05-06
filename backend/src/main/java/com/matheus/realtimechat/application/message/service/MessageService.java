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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserContactRepository userContactRepository;

    @Transactional
    public MessageResponse sendMessage(UUID userId, MessageRequest request){
        UserContact userContact = getUserContact(userId, request.contactId());

        Message message = new Message(request.message(), userContact);

        messageRepository.save(message);

        return toResponse(message);
    }

    public List<MessageResponse> findMessages(UUID userId, UUID contactId){
        UserContact user = getUserContact(userId, contactId);
        UserContact contact = getUserContact(contactId, userId);

        return Stream.concat(
                user.getMessages().stream(),
                contact.getMessages().stream()

        ).sorted(Comparator.comparing(Message::getCreatedAt))
         .map(this::toResponse)
         .toList();
    }

    private MessageResponse toResponse(Message message){
        return new MessageResponse(
                message.getContent(),
                message.getUserContact().getUser().getId(),
                message.getUserContact().getUser().getUsername(),
                message.getCreatedAt()
        );
    }

    private UserContact getUserContact(UUID userId, UUID contactId) {
        return userContactRepository
                .findByUser_IdAndContact_Id(userId, contactId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "UserContact NotFound"));
    }
}

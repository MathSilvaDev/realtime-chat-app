package com.matheus.realtimechat.application.contact.service;

import com.matheus.realtimechat.application.contact.dto.request.ContactResponse;
import com.matheus.realtimechat.domain.user.entity.User;
import com.matheus.realtimechat.domain.user.repository.UserRepository;
import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
import com.matheus.realtimechat.domain.usercontact.repository.UserContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private UserContactRepository userContactRepository;
    private final UserRepository userRepository;

    public List<ContactResponse> findContacts(UUID userId){

        return userContactRepository.findByUser_Id(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private ContactResponse toResponse(UserContact userContact){
        User contact = userContact.getContact();

        return new ContactResponse(
                contact.getId(),
                contact.getHandleName(),
                contact.getUsername()
        );
    }
}

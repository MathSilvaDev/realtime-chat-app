package com.matheus.realtimechat.application.contact.service;

import com.matheus.realtimechat.application.contact.dto.response.ContactResponse;
import com.matheus.realtimechat.application.contact.dto.request.ContactRequest;
import com.matheus.realtimechat.domain.user.entity.User;
import com.matheus.realtimechat.domain.user.repository.UserRepository;
import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
import com.matheus.realtimechat.domain.usercontact.repository.UserContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final UserContactRepository userContactRepository;
    private final UserRepository userRepository;

    public List<ContactResponse> findContacts(UUID userId){

        return userContactRepository.findByUser_Id(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public ContactResponse addContact(UUID userId, ContactRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "ERROR: User doesn't exists"));

        User contact = userRepository.findByUsername(request.username().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "This contact doesn't exists"));

        if(user.getId().equals(contact.getId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You cannot add yourself as a contact");
        }

        boolean existsContact  =
                userContactRepository.existsByUser_IdAndContact_Id(userId, contact.getId());

        if(existsContact){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Contact already exists");
        }

        UserContact userContact = new UserContact(user, contact);

        userContactRepository.save(userContact);

        return toResponse(userContact);
    }

    private ContactResponse toResponse(UserContact userContact){
        User contact = userContact.getContact();

        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getUsername()
        );
    }
}

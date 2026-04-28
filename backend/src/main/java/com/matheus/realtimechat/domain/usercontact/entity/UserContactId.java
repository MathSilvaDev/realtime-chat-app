package com.matheus.realtimechat.domain.usercontact.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContactId implements Serializable {

    private UUID userId;
    private UUID contactId;

    public UserContactId(UUID userId, UUID contactId){
        this.userId = userId;
        this.contactId = contactId;
    }
}

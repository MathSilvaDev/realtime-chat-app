package com.matheus.realtimechat.domain.usercontact.entity;

import com.matheus.realtimechat.domain.message.entity.Message;
import com.matheus.realtimechat.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_contact")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContact {

    @EmbeddedId
    private UserContactId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contactId")
    private User contact;

    @OneToMany(mappedBy = "userContact", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    public UserContact(User user, User contact){
        this.id = new UserContactId(user.getId(), contact.getId());
        this.user = user;
        this.contact = contact;
    }
}

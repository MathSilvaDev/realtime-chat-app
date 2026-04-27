package com.matheus.realtimechat.domain.user.entity;

import com.matheus.realtimechat.domain.contact.entity.Contact;
import com.matheus.realtimechat.domain.role.entity.Role;
import com.matheus.realtimechat.common.validation.ValidationConstants;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String handleName;

    @Column(nullable = false, length = ValidationConstants.USERNAME_MAX_LENGTH)
    private String username;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @ManyToMany
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tb_user_contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private Set<Contact> contacts = new HashSet<>();

    public User(String username, String password){
        this.username = username;
        this.password = password;
        handleName = generateHandleName();
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    private String generateHandleName(){
        return username + "#" +
                UUID.randomUUID().toString().substring(0, 8);
    }
}

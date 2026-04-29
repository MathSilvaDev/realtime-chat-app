package com.matheus.realtimechat.domain.user.entity;

import com.matheus.realtimechat.domain.role.entity.Role;
import com.matheus.realtimechat.common.validation.ValidationConstants;
import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
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

    @Column(nullable = false, length = ValidationConstants.USERNAME_MAX_LENGTH)
    private String name;

    @Column(nullable = false, unique = true, length = ValidationConstants.USERNAME_MAX_LENGTH)
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

    @OneToMany(mappedBy = "user")
    private Set<UserContact> contacts = new HashSet<>();

    private User(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;;
    }

    public static User create(String name, String username, String password){
        return new User(
                name,
                username.toLowerCase(),
                password
        );
    }

    public void addRole(Role role){
        roles.add(role);
    }
}

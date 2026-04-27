package com.matheus.realtimechat.domain.contact.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "contact")
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String handleName;
}

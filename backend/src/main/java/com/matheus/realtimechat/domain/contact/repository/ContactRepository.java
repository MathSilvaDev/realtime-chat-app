package com.matheus.realtimechat.domain.contact.repository;

import com.matheus.realtimechat.domain.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}

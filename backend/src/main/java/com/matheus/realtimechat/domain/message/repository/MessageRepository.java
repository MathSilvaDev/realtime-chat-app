package com.matheus.realtimechat.domain.message.repository;

import com.matheus.realtimechat.domain.message.entity.Message;
import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByUserContact(UserContact userContact);
}

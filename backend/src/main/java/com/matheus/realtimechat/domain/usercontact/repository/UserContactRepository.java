package com.matheus.realtimechat.domain.usercontact.repository;

import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
import com.matheus.realtimechat.domain.usercontact.entity.UserContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, UserContactId> {

    List<UserContact> findByUser_Id(UUID userId);
    boolean existsByUser_IdAndContact_Id(UUID userId, UUID contactId);
}

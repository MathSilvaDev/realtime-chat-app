package com.matheus.realtimechat.domain.usercontact.repository;

import com.matheus.realtimechat.domain.user.entity.User;
import com.matheus.realtimechat.domain.usercontact.entity.UserContact;
import com.matheus.realtimechat.domain.usercontact.entity.UserContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, UserContactId> {

    List<UserContact> findByUser(User user);
    boolean existsByUserAndContact(User user, User contact);
}

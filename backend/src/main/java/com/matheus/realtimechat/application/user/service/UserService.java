package com.matheus.realtimechat.application.user.service;

import com.matheus.realtimechat.application.user.dto.response.UserInfoResponse;
import com.matheus.realtimechat.domain.user.entity.User;
import com.matheus.realtimechat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse userInfo(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Not Found"));

        return toInfoResponse(user);
    }

    private UserInfoResponse toInfoResponse(User user){
        return new UserInfoResponse(
                user.getUsername(),
                user.getHandleName(),
                user.getCreatedAt()
        );
    }

}

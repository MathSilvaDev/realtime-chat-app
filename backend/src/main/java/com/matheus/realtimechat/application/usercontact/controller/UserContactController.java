package com.matheus.realtimechat.application.usercontact.controller;

import com.matheus.realtimechat.application.usercontact.service.UserContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-contact")
@RequiredArgsConstructor
public class UserContactController {

    private final UserContactService userContactService;
}

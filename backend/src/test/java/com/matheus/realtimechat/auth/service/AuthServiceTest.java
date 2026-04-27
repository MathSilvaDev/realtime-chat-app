package com.matheus.realtimechat.auth.service;

import com.matheus.realtimechat.auth.dto.request.LoginRequest;
import com.matheus.realtimechat.auth.dto.request.RegisterRequest;
import com.matheus.realtimechat.auth.dto.response.LoginResponse;
import com.matheus.realtimechat.domain.role.entity.Role;
import com.matheus.realtimechat.domain.role.enums.RoleName;
import com.matheus.realtimechat.domain.role.repository.RoleRepository;
import com.matheus.realtimechat.domain.user.entity.User;
import com.matheus.realtimechat.domain.user.repository.UserRepository;
import com.matheus.realtimechat.infrastructure.security.jwt.JwtService;
import com.matheus.realtimechat.infrastructure.security.jwt.dto.TokenData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private final String email = "test@test.test";

    @Nested
    class Register{

        @Test
        void shouldRegisterWithSuccess(){
            RegisterRequest request = new RegisterRequest(
                    "test@test.test",
                    "username",
                    "rawPassword"
            );

            Role role = new Role(RoleName.BASIC);

            when(userRepository.existsByEmail(request.email()))
                    .thenReturn(false);

            when(roleRepository.findByName(role.getName()))
                    .thenReturn(Optional.of(role));

            when(passwordEncoder.encode(request.password()))
                    .thenReturn("hashedPassword");

            authService.register(request);

            verify(userRepository).save(argThat(user ->
                    user.getEmail().equals(request.email()) &&
                    user.getUsername().equals(request.username()) &&
                    user.getPassword().equals("hashedPassword")
            ));


        }
    }

    @Nested
    class Login{

        @Test
        void shouldLoginWithSuccess(){
            LoginRequest request = new LoginRequest(
                    email,
                    "hashPassword"
            );

            User user = new User(
                    email,
                    "username",
                    "hashPassword"
            );

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities("ROLE_BASIC")
                    .build();

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null);

            when(authenticationManager.authenticate(any()))
                    .thenReturn(authentication);

            when(userRepository.findByEmail(user.getEmail()))
                    .thenReturn(Optional.of(user));

            when(jwtService.generateToken(user))
                    .thenReturn(new TokenData("token-test", 300L));

            LoginResponse response = authService.login(request);

            assertEquals("token-test", response.token());
            assertEquals(300L, response.expiresAt());
        }

        @Test
        void shouldThrowWhenAuthenticationFails(){
            LoginRequest request = new LoginRequest(
                    email,
                    "wrongPassword"
            );

            when(authenticationManager.authenticate(any()))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            assertThrows(BadCredentialsException.class,
                    () -> authService.login(request));
        }
    }
}
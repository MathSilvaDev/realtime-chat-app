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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request){

        if(userRepository.existsByEmail(request.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Role roleBasic = roleRepository.findByName(RoleName.BASIC).
                orElseThrow(() -> new IllegalStateException("Default role BASIC not found"));

        String hashPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.email(),
                request.username(),
                hashPassword
        );

        user.addRole(roleBasic);

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        TokenData tokenData = jwtService.generateToken(user);

        return new LoginResponse(tokenData.token(), tokenData.expiresAt());
    }
}

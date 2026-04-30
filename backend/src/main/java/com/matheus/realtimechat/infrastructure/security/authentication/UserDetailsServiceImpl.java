package com.matheus.realtimechat.infrastructure.security.authentication;

import com.matheus.realtimechat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsernameIgnoreCase(username)
                .map(user -> User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(
                                user.getRoles().stream()
                                        .map(role ->
                                                new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                        .toList()
                        )
                        .build()
                ).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }
}

package com.matheus.realtimechat.infrastructure.security.config;

import com.matheus.realtimechat.domain.role.entity.Role;
import com.matheus.realtimechat.domain.role.enums.RoleName;
import com.matheus.realtimechat.domain.role.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner runnerRole(RoleRepository roleRepository){
        return args -> {
            for (RoleName name: RoleName.values()){
                if(roleRepository.findByName(name).isEmpty()){
                    Role role = new Role(name);
                    roleRepository.save(role);
                }
            }
        };
    }
}

package com.matheus.realtimechat.domain.role.repository;

import com.matheus.realtimechat.domain.role.entity.Role;
import com.matheus.realtimechat.domain.role.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}

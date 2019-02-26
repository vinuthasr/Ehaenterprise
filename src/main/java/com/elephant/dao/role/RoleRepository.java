package com.elephant.dao.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elephant.domain.roles.Role;
import com.elephant.domain.roles.RoleName;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String string);
}
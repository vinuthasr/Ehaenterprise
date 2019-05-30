package com.elephant.dao.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elephant.domain.roles.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String string);
}
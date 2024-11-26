package com.user_management_app.repository;

import com.user_management_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Object> findByName(String name);

    Set<Role> findByIdIn(Set<Long> roleIds);

    boolean existsByName(String name);


}

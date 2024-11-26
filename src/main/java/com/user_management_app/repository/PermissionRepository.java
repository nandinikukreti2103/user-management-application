package com.user_management_app.repository;

import com.user_management_app.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
    Optional<Object> findByName(String name);

    Optional<Permission> findByRoles_Id(Long roleId);


}

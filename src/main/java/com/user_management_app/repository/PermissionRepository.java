package com.user_management_app.repository;

import com.user_management_app.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
    Optional<Object> findByName(String name);

}

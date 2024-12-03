package com.user_management_app.repository;

import com.user_management_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByIdIn(Set<Long> roleIds);

    boolean existsByName(String name);


}

package com.user_management_app.repository;

import com.user_management_app.entity.Role;
import com.user_management_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoles(Role role);

    Optional<User> findById(Long id);

}

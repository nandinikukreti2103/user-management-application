package com.user_management_app.repository;

import com.user_management_app.entity.Role;
import com.user_management_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoles(Role role);

    Optional<User> findById(Long id);


    @Query("SELECT u FROM User u WHERE u.id IN " +
            "(SELECT ur.id FROM Role r JOIN r.users ur WHERE r.name = :roleName)")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);


    @Query("SELECT u FROM User u WHERE u.firstName = :firstName")
    List<User> findByFirstName(@Param("firstName") String firstName);


}

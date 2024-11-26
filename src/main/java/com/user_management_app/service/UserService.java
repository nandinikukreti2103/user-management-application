package com.user_management_app.service;

import com.user_management_app.dto.UserDto;
import com.user_management_app.entity.Permission;
import com.user_management_app.entity.Role;
import com.user_management_app.entity.User;
import com.user_management_app.repository.PermissionRepository;
import com.user_management_app.repository.RoleRepository;
import com.user_management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    /**
     * Get a user by their ID.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    /**
     * Get all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update an existing user.
     */
    public User updateUser(Long id, User userDetails) {
        // Fetch the existing user by ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update user details
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setGender(userDetails.getGender());

        // If roles are provided, update them
        if (userDetails.getRoles() != null) {
            existingUser.setRoles(userDetails.getRoles());
        }

        // Save and return the updated user
        return userRepository.save(existingUser);
    }

    /**
     * Delete a user by their ID.
     */
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


    /**
     * Add a new role.
     */
    public Role addRole(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new RuntimeException("Role already exists with name: " + role.getName());
        }
        return roleRepository.save(role);
    }

    /**
     * Assign a role to a user.
     */
    public User assignRoleToUser(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    /**
     * Remove a role from a user.
     */
    public User removeRoleFromUser(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    public User createUser(UserDto userDto) {
        // Create new user from DTO
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());


        Set<Role> roles = roleRepository.findByIdIn(userDto.getRoleIds());
        user.setRoles(roles);

        return userRepository.save(user);
    }


    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public Map<String, List<String>> getUserRolePermissionNameByUserId(Long userId) {
        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Extract roles from the user
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName) // Ensure Role::getRoleName returns a String
                .collect(Collectors.toList());

        // Extract permissions from the roles
        List<String> permissionNames = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getPermissionName) // Ensure Permission::getPermissionName returns a String
                .collect(Collectors.toList());

        // Prepare the result
        Map<String, List<String>> result = new HashMap<>();
        result.put("username", Collections.singletonList(user.getUsername().toString())); // Single username
        result.put("roles", roleNames); // List of role names
        result.put("permissions", permissionNames); // List of permission names

        return result;
    }

}




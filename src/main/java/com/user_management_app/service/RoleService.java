package com.user_management_app.service;

import com.user_management_app.entity.Role;
import com.user_management_app.entity.User;
import com.user_management_app.repository.RoleRepository;
import com.user_management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    /**
     * Get all roles.
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Get a role by its ID.
     */
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    /**
     * Create a new role.
     */

    public Role createRole(Role role) {
        // Check if a role with the same name already exists
        if (roleRepository.existsByName(role.getName())) {
            throw new RuntimeException("Role already exists with name: " + role.getName());
        }
        return roleRepository.save(role);
    }


    /**
     * Update an existing role.
     */
    public Role updateRole(Long id, Role roleDetails) {
        // Find the role to update
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        // Update role fields
        existingRole.setName(roleDetails.getName());
        existingRole.setDescription(roleDetails.getDescription());

        // Save and return the updated role
        return roleRepository.save(existingRole);
    }

    /**
     * Delete a role by its ID.
     */
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    //add role to user
    public Role addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        // Add role to user
        user.getRoles().add(role);
        userRepository.save(user);

        return role;
    }


    //Get all usernames of users assigned to a particular role ID.
    public List<Object> getUsernamesByRoleId(Long roleId) {
        // Find the role by ID
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        // Find all users with the given role
        List<User> users = userRepository.findByRoles(role);

        // Extract the usernames from the user list
        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

}

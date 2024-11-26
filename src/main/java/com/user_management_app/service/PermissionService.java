package com.user_management_app.service;

import com.user_management_app.entity.Permission;
import com.user_management_app.entity.Role;
import com.user_management_app.repository.PermissionRepository;
import com.user_management_app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    /**
     * Get all permissions.
     */
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    /**
     * Get a permission by its ID.
     */
    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
    }

    /**
     * Create a new permission.
     */
    public Permission createPermission(Permission permission) {
        // Check if a permission with the same name already exists
        if (permissionRepository.findByName(permission.getName()).isPresent()) {
            throw new RuntimeException("Permission already exists with name: " + permission.getName());
        }
        return permissionRepository.save(permission);
    }

    /**
     * Update an existing permission.
     */
    public Permission updatePermission(Long id, Permission permissionDetails) {
        // Find the permission to update
        Permission existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));

        // Update permission fields
        existingPermission.setName(permissionDetails.getName());
        existingPermission.setDescription(permissionDetails.getDescription());

        // Save and return the updated permission
        return permissionRepository.save(existingPermission);
    }

    /**
     * Delete a permission by its ID.
     */
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new RuntimeException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

    // Add permission to a role
    public Permission addPermissionToRole(Long roleId, Long permissionId) {
        // Retrieve role and permission entities from their respective repositories
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionId));

        // Add permission to role (Many-to-Many relationship)
        role.getPermissions().add(permission);

        // Save the updated role
        roleRepository.save(role);

        return permission; // Return the assigned permission as a response
    }

    // Remove permission from a role
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        // Retrieve role and permission entities from their respective repositories
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionId));

        // Remove permission from role's permissions set
        role.getPermissions().remove(permission);

        // Save the updated role
        roleRepository.save(role);
    }

}

package com.user_management_app.service;

import com.user_management_app.dto.UserDto;
import com.user_management_app.entity.Permission;
import com.user_management_app.entity.Role;
import com.user_management_app.entity.User;
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


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User updateUser(Long id, User userDetails) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));


        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setGender(userDetails.getGender());
        existingUser.setEmail(userDetails.getEmail());


        if (userDetails.getRoles() != null) {
            existingUser.setRoles(userDetails.getRoles());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public Role addRole(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new RuntimeException("Role already exists with name: " + role.getName());
        }
        return roleRepository.save(role);
    }

    public User assignRoleToUser(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public User removeRoleFromUser(Long userId, Long roleId) {
        User user = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    public User createUser(UserDto userDto) {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());


        Set<Role> roles = roleRepository.findByIdIn(userDto.getRoleIds());
        user.setRoles(roles);

        return userRepository.save(user);
    }


    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public Map<String, List<String>> getUserRolePermissionNameByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));


        List<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        List<String> permissionNames = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getPermissionName)
                .collect(Collectors.toList());

        Map<String, List<String>> result = new HashMap<>();
        result.put("username", Collections.singletonList(user.getUsername().toString()));
        result.put("roles", roleNames);
        result.put("permissions", permissionNames);

        return result;
    }

}




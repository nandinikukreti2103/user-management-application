package com.user_management_app.service;

import com.user_management_app.dto.UserDto;
import com.user_management_app.entity.Role;
import com.user_management_app.entity.User;
import com.user_management_app.repository.RoleRepository;
import com.user_management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRoleName(roleName);
    }

    public List<User> findUsersByNameContains(String firstName) {
        return userRepository.findByFirstName(firstName);
    }
}




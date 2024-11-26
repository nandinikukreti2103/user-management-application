package com.user_management_app.dto;

import com.user_management_app.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private String gender;
    private Set<Long> roleIds; // Set of role IDs

    private List<String> roles;
    private List<String> permissions;
}

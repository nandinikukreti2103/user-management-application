package com.user_management_app.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private Set<Long> roleIds;

    private List<String> roles;
    private List<String> permissions;
}

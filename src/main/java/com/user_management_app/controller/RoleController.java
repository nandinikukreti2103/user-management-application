package com.user_management_app.controller;

import com.user_management_app.entity.Role;
import com.user_management_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/roles")
    public class RoleController {

        @Autowired
        private RoleService roleService;

        @GetMapping("/getAll")
        public List<Role> getAllRoles() {
            return roleService.getAllRoles();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(role);
        }

        @PostMapping("/create")
        public Role createRole(@RequestBody Role role) {
            return roleService.createRole(role);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
            Role updatedRole = roleService.updateRole(id, roleDetails);
            return ResponseEntity.ok(updatedRole);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        }


        @PostMapping("/assign-role-to-user/{userId}/{roleId}")
        public ResponseEntity<Role> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
            Role role = roleService.addRoleToUser(userId, roleId);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }

        @GetMapping("/get-all-users-assign-to-role/{id}")
        public ResponseEntity<List<Object>> getUsersAssignToRoleId(@PathVariable("id") Long roleId) {
            List<Object> usernames = roleService.getUsernamesByRoleId(roleId);
            return ResponseEntity.ok(usernames);
        }


    }



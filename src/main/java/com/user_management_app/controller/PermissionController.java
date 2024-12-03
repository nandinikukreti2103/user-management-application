package com.user_management_app.controller;

import com.user_management_app.entity.Permission;
import com.user_management_app.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/getAll")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @PostMapping("/create")
    public Permission createPermission(@RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permissionDetails) {
        Permission updatedPermission = permissionService.updatePermission(id, permissionDetails);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign-permission-to-role/{roleId}/{permissionId}")
    public ResponseEntity<Permission> assignPermissionToRole(@PathVariable("roleId") Long roleId, @PathVariable("permissionId") Long permissionId) {
        Permission permission = permissionService.addPermissionToRole(roleId, permissionId);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }

      @DeleteMapping("/remove-permission-from-role/{roleId}/{permissionId}")
    public ResponseEntity<Void> removePermissionFromRole(@PathVariable("roleId") Long roleId, @PathVariable("permissionId") Long permissionId) {
        permissionService.removePermissionFromRole(roleId, permissionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

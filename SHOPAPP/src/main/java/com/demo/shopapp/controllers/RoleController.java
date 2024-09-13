package com.demo.shopapp.controllers;

import com.demo.shopapp.model.Role;
import com.demo.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/{roleId}")
    public ResponseEntity<String> getRoleName(@PathVariable Long roleId) {
        try {
            String roleName = roleService.getRoleNameById(roleId);
            return ResponseEntity.ok(roleName);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Role not found: " + e.getMessage());
        }
    }
}

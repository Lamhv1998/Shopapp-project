package com.demo.shopapp.controllers;

import com.demo.shopapp.dtos.UserDTO;
import com.demo.shopapp.dtos.UserLoginDTO;
import com.demo.shopapp.services.RoleService;
import com.demo.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService  roleService;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                response.put("message", "Password and Retype Password do not match");
                return ResponseEntity.badRequest().body(response);
            }
            userService.createUser(userDTO);  // Tạo tài khoản người dùng
            response.put("message", "Register successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error occurred: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO UserDTO) throws Exception {
        try {
            String token = userService.loginUser(UserDTO.getPhoneNumber(), UserDTO.getPassword(),UserDTO.getRoleId());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred: " + e.getMessage());
        }

    }

}


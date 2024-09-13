package com.demo.shopapp.services;

import com.demo.shopapp.model.Role;
import com.demo.shopapp.repositories.RoleRepository;
import com.demo.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImple implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public String getRoleNameById(Long id) {
            Optional<Role> role = roleRepository.findById(id);
            if (role.isPresent()) {
                return role.get().getName();
            } else {
                throw new RuntimeException("Role not found");
            }
        }

}

package com.demo.shopapp.services;
import com.demo.shopapp.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface RoleService {

    List<Role> getAllRoles();

    String getRoleNameById(Long roleId);
}

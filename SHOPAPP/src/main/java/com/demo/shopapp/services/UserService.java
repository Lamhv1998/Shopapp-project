package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.UserDTO;
import com.demo.shopapp.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User createUser(UserDTO userDTO) throws DatanotFoundException;

    String loginUser(String phoneNumber, String password,Long roleId) throws Exception;

    String getUserRoleNameByPhoneNumber(String phoneNumber) throws DatanotFoundException;

}

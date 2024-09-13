package com.demo.shopapp.repositories;

import com.demo.shopapp.model.Role;
import com.demo.shopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phone);

    Optional<User> findByPhoneNumber(String phoneNumber);
}

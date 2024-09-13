package com.demo.shopapp.repositories;

import com.demo.shopapp.model.Order;
import com.demo.shopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userID);



}

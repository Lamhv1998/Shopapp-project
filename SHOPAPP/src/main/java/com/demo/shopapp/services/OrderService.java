package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.OrdersDTO;
import com.demo.shopapp.model.Order;
import com.demo.shopapp.responses.OrderReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    Order createOrder(OrdersDTO ordersDTO) throws Exception;
    Order getOrderById(Long id);

    Order updateOrder(Long id, OrdersDTO ordersDTO) throws DatanotFoundException;

    void deleteOrder(Long id);

    Page<OrderReponse> getAllOrders(PageRequest pageRequest);
    List<Order> findByUserId(Long userId);
}

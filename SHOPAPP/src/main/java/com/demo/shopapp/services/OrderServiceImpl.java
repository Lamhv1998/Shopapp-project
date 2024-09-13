package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.OrdersDTO;
import com.demo.shopapp.model.Order;
import com.demo.shopapp.model.OrderStatus;
import com.demo.shopapp.model.User;
import com.demo.shopapp.repositories.OrderRepository;
import com.demo.shopapp.repositories.UserRepository;
import com.demo.shopapp.responses.OrderReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    // CHECK USER ID
    public Order createOrder(OrdersDTO ordersDTO) throws Exception {
        User user = userRepository.findById(ordersDTO.getUserId()).orElseThrow(()
                -> new DatanotFoundException("Không tìm thấy user với id này:" + ordersDTO.getUserId()));
        Order newOrder = Order.builder()
                .name(ordersDTO.getFullName())
                .email(ordersDTO.getEmail())
                .phoneNumber(ordersDTO.getPhoneNumber())
                .address(ordersDTO.getAddress())
                .note(ordersDTO.getNote())
                .totalMoney(ordersDTO.getTotalMoney())
                .shippingMethod(ordersDTO.getShippingMethod())
                .shippingAddress(ordersDTO.getShippingAddress())
                .paymentMethod(ordersDTO.getPaymentMethod())
                .build();
        newOrder.setUser(user);
        newOrder.setOrderDate(new Date());
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setActive(true);
        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrdersDTO ordersDTO) throws DatanotFoundException {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() ->
                new DatanotFoundException("Can not find order with id " + id));
        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new DatanotFoundException("Can not find order with id " + id));
        Order.builder()
                .name(ordersDTO.getFullName())
                .email(ordersDTO.getEmail())
                .phoneNumber(ordersDTO.getPhoneNumber())
                .address(ordersDTO.getAddress())
                .note(ordersDTO.getNote())
                .totalMoney(ordersDTO.getTotalMoney())
                .shippingMethod(ordersDTO.getShippingMethod())
                .shippingAddress(ordersDTO.getShippingAddress())
                .paymentMethod(ordersDTO.getPaymentMethod())
                .build();
        existingOrder.setUser(existingUser);
        existingOrder.setOrderDate(new Date());
        existingOrder.setStatus(OrderStatus.PENDING);
        existingOrder.setActive(true);
        return orderRepository.save(existingOrder);

    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setActive(false);
        }
        orderRepository.save(order);
    }

    @Override
    public Page<OrderReponse> getAllOrders(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}





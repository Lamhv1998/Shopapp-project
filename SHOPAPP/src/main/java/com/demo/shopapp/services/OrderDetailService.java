package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.OrderDetailDTO;
import com.demo.shopapp.dtos.OrdersDTO;
import com.demo.shopapp.model.Order;
import com.demo.shopapp.model.OrderDetail;
import com.demo.shopapp.responses.OrderReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetail getOrderDetailById(Long id) throws Exception;

    OrderDetail updateOrderDetail(Long orderId, OrderDetailDTO orderDetailDTO) throws DatanotFoundException;

    void deleteOrderDetail(Long id) throws DatanotFoundException;

    List<OrderDetail> findByOrderId(Long orderId);
}


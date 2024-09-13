package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.OrderDetailDTO;
import com.demo.shopapp.dtos.OrdersDTO;
import com.demo.shopapp.model.*;
import com.demo.shopapp.repositories.OrderDetailRepository;
import com.demo.shopapp.repositories.OrderRepository;
import com.demo.shopapp.repositories.ProductRepository;
import com.demo.shopapp.responses.OrderReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        // tim xem order co ton tai khong
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(() ->
                new DatanotFoundException("Khong tim thay order voi id nay" + orderDetailDTO.getOrderId()));
        // tim product theo id
        Product product = productRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(() ->
                new DatanotFoundException("Khong tim thay product voi id nay" + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }
    @Override
    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public OrderDetail updateOrderDetail(Long orderId, OrderDetailDTO orderDetailDTO) throws DatanotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderId).orElseThrow(() ->
                new DatanotFoundException("Can not find order with id " + orderId));
        Product existingProduct = productRepository.findById(orderId).orElseThrow(() ->
                new DatanotFoundException("Can not find order with id " + orderId));
        OrderDetail.builder()
                .product(existingProduct)
                .price(orderDetailDTO.getPrice())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) throws DatanotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElseThrow(() ->
                new DatanotFoundException("Can not find order with id " + id));
        if (existingOrderDetail != null) {
            orderDetailRepository.deleteById(id);
        }
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}

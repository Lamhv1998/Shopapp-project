package com.demo.shopapp.controllers;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.OrderDetailDTO;
import com.demo.shopapp.model.OrderDetail;
import com.demo.shopapp.responses.OrderDetailResponse;
import com.demo.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/order_details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<?> createOrderdetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) throws Exception {
    OrderDetail newOrderDetail =   orderDetailService.createOrderDetail(orderDetailDTO);  // Tao moi order detail moi
        return ResponseEntity.ok().body(OrderDetailResponse.formOrderDetail(newOrderDetail));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) throws Exception {
         orderDetailService.getOrderDetailById(id);
        return ResponseEntity.ok("Get OrderDetail by ID: " + id);
    }

    // Lay ra list orderdetail của order nao do
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        return ResponseEntity.ok(orderDetails);
        // Lay ra order detail by order id
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("orderId") Long orderId,
                                               @RequestBody OrderDetailDTO newOrderDetailDTO) throws DatanotFoundException {
        OrderDetail  updateOrderDetail = orderDetailService.updateOrderDetail(orderId, newOrderDetailDTO);
        return ResponseEntity.ok("Update OrderDetail by Order ID = " + orderId + "newOrderDetailData:" + updateOrderDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) throws DatanotFoundException {
        orderDetailService.deleteOrderDetail(id);  // Xoa order detail theo id
        return ResponseEntity.ok("Delete OrderDetail by ID: " + id);
    }
}

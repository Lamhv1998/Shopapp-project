package com.demo.shopapp.controllers;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.CategoryDTO;
import com.demo.shopapp.dtos.OrdersDTO;
import com.demo.shopapp.model.Order;
import com.demo.shopapp.model.Product;
import com.demo.shopapp.responses.OrderListResponse;
import com.demo.shopapp.responses.OrderReponse;
import com.demo.shopapp.responses.ProductListResponse;
import com.demo.shopapp.responses.ProductResponse;
import com.demo.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrdersController {
    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<OrderListResponse> getOrder(@RequestParam("page") int page,
                                                      @RequestParam("limit") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<OrderReponse> orderPage = orderService.getAllOrders(pageRequest);
        int totalPages = orderPage.getTotalPages();
        List<OrderReponse> orders = orderPage.getContent();
        return ResponseEntity.ok(OrderListResponse
                .builder()
                .orders(orders)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long user_id) {
        List<Order> orders = orderService.findByUserId(user_id);
        return ResponseEntity.ok("Lay ra danh sach order tu user_id " + orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdersById(@Valid @PathVariable("id") Long order_Id) {
        Order existingOrder = orderService.getOrderById(order_Id);
        return ResponseEntity.ok("Lay chi tiet 1 order tu order_id" + existingOrder);
    }

    @PostMapping("")
    public ResponseEntity<?> createtOrders(@Valid @RequestBody OrdersDTO ordersDTO) throws Exception {
        Order newOrder = orderService.createOrder(ordersDTO);
        return ResponseEntity.ok("Create Order sucssesfull" + newOrder);
        // tạo order
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrders(@PathVariable long id,
                                               @Valid @RequestBody OrdersDTO ordersDTO) throws DatanotFoundException {
        Order newOrder = orderService.updateOrder(id, ordersDTO);
        return ResponseEntity.ok("Update Order with id: " + id + newOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrders(@PathVariable long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Deleted thanh cong Order with id:" + id);
    }
}



package com.demo.shopapp.responses;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.model.Order;
import com.demo.shopapp.model.OrderStatus;
import com.demo.shopapp.model.Product;
import com.demo.shopapp.model.User;
import com.demo.shopapp.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderReponse extends BaseResponse {
    private final UserRepository userRepository;
    private Long id;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("fullname")
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private String address;
    private String note;
    @Column(name = "order_date")
    private Date orderDate;
    private String status;
    @JsonProperty("total_money")
    private Float totalMoney;
    @JsonProperty("shipping_method")
    @Column(name = "shipping_method")
    private String shippingMethod;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "shipping_date")
    private Date shippingDate;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "tracking_number")
    private LocalDateTime trackingNumber;
    @Column(name = "active")
    private Boolean active;

//    public static OrderReponse formProduct(Order order){
//        OrderReponse orderReponse = OrderReponse.builder()
//        Order newOrder = Order.builder()
//                .name(order.getName())
//                .email(order.getEmail())
//                .phoneNumber(order.getPhoneNumber())
//                .address(order.getAddress())
//                .note(order.getNote())
//                .totalMoney(order.getTotalMoney())
//                .shippingMethod(order.getShippingMethod())
//                .shippingAddress(order.getShippingAddress())
//                .paymentMethod(order.getPaymentMethod())
//                .build();
//        newOrder.setUser(user);
//        newOrder.setOrderDate(new Date());
//        newOrder.setStatus(OrderStatus.PENDING);
//        newOrder.setActive(true);
//        return orderRepository.save(newOrder);
//    }

}

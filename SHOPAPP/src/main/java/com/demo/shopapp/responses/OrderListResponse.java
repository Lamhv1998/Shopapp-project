package com.demo.shopapp.responses;

import lombok.Builder;

import java.util.List;
@Builder
public class OrderListResponse {
    private List<OrderReponse> orders;
    private int totalPages;

}

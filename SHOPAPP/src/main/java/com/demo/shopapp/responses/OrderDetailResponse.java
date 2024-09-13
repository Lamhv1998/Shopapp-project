package com.demo.shopapp.responses;

import com.demo.shopapp.model.OrderDetail;
import com.demo.shopapp.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    @JsonProperty("order_id")
    private Long order;
    @JsonProperty("product_id")
    private Long product;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("number_of_product")
    private long numberOfProducts;
    @JsonProperty("total_money")
    private Float totalMoney;
    @JsonProperty("color")
    private String color;

    public static OrderDetailResponse formOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse
                .builder()
                .id(orderDetail.getId())
                .order(orderDetail.getOrder().getId())
                .product(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();

    }
}

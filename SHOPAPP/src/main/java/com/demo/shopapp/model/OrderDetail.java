package com.demo.shopapp.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_details")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column (name = "price",nullable = false)
    private Float price;
    @Column (name = "number_of_product",nullable = false)
    private long numberOfProducts;
    @Column(name = "total_money",nullable = false)
    private Float totalMoney;
    @Column(name = "color")
    private String color;
}

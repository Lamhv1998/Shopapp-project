package com.demo.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product_images")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "url", length = 300)
    private String url;
}

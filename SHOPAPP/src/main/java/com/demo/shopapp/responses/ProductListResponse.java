package com.demo.shopapp.responses;
import lombok.*;
import java.util.List;

@Data // toString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}

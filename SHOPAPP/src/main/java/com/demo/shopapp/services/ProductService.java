package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.ProductDTO;
import com.demo.shopapp.dtos.ProductImageDTO;
import com.demo.shopapp.model.Product;
import com.demo.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Product createProduct(ProductDTO productDTO) throws DatanotFoundException;

    Product getProductById(Long id) throws DatanotFoundException, Exception;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest) throws Exception;

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id) throws Exception;

    boolean existsByName(String name);

    Product createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}

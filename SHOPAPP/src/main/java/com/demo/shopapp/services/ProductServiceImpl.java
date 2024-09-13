package com.demo.shopapp.services;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.Exception.InvalidParamException;
import com.demo.shopapp.dtos.ProductDTO;
import com.demo.shopapp.dtos.ProductImageDTO;
import com.demo.shopapp.model.Category;
import com.demo.shopapp.model.Product;
import com.demo.shopapp.model.ProductImage;
import com.demo.shopapp.repositories.CategoryRepository;
import com.demo.shopapp.repositories.ProductImageRepository;
import com.demo.shopapp.repositories.ProductRepository;
import com.demo.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DatanotFoundException {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DatanotFoundException(
                        "Can not find category with id:" + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .url(productDTO.getUrl())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId).
                orElseThrow(() -> new DatanotFoundException("Can not find product with id =" + productId));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) throws Exception {
        return productRepository.findAll(pageRequest).map(ProductResponse::formProduct);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DatanotFoundException(
                            "Can not find category with id:" + productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setUrl(productDTO.getUrl());
            existingProduct.setCategory(existingCategory);
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Product createProductImage(Long productId, ProductImageDTO productImageDTO) throws DatanotFoundException, InvalidParamException {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new DatanotFoundException("Can not find product with id =" + productId));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .url(productImageDTO.getUrl())
                .build();
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= 5) {
            throw new InvalidParamException("Product with id =" + productId + " already has 5 images");
        }
        productImageRepository.save(newProductImage);
        return existingProduct;
    }
}

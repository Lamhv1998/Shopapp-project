package com.demo.shopapp.controllers;

import com.demo.shopapp.Exception.DatanotFoundException;
import com.demo.shopapp.dtos.ProductDTO;
import com.demo.shopapp.dtos.ProductImageDTO;
import com.demo.shopapp.model.Product;
import com.demo.shopapp.responses.ProductListResponse;
import com.demo.shopapp.responses.ProductResponse;
import com.demo.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.tokens.AliasToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProduct(@RequestParam("page") int page,
                                                          @RequestParam("limit") int limit) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) throws Exception {
        Product existingProduct = productService.getProductById(productId);
        return ResponseEntity.ok(ProductResponse.formProduct(existingProduct));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                                @RequestParam("files") List<MultipartFile> files) throws Exception {
        if (files == null || files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("At least one file is required");
        }
        Product newProduct = productService.createProduct(productDTO);

        for (MultipartFile item : files) {
            if (item != null) {
                if (item.getSize() > 10 * 1024 * 1024) { // 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File size should be less than or equal to 10MB");
                }

                String contentType = item.getContentType();
                if (contentType == null || !contentType.startsWith("image/jpeg")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image/jpeg");
                }

                // Save the file and create a ProductImage
                String filename = storeFile(item);
                productService.createProductImage(
                        newProduct.getId(),
                        ProductImageDTO.builder()
                                .url(filename)
                                .build()
                );
            }
        }
        return ResponseEntity.ok("Create Product successfully" + newProduct);
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        java.nio.file.Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) throws Exception {
        Product updateProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok("Update product with id: " + id + updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) throws Exception {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete product with id =%d succcesfully " + id);
    }

//    @GetMapping("/images/{imageName")
//    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
//        try {
//            java.nio.file.Path imagePath = Path.of("uploads/" + imageName);
//            UrlResource resource = new UrlResource(imagePath.toUri());
//            if (resource.exists()) {
//                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(Files.readAllBytes(resource.getFile().toPath()));
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}

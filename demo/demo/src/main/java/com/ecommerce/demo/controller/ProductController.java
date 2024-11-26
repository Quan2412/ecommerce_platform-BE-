package com.ecommerce.demo.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.demo.dto.ProductDTO;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.service.CloudinaryService;
import com.ecommerce.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @RequestParam("product") Product product,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<?> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("stockQuantity") Integer stockQuantity
    ) {
        try {
            // Upload image to Cloudinary
            String imageUrl = cloudinaryService.uploadImage(image);

            // Create product with image URL
            Product product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price.doubleValue());
            product.setStockQuantity(stockQuantity);
            product.setImageUrl(imageUrl);

            Product savedProduct = productService.save(product);
            
            return ResponseEntity.ok(convertToDTO(savedProduct));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to create product: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> updateProductImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            Product product = productService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Upload new image
            String imageUrl = cloudinaryService.uploadImage(image);
            
            // Update product
            product.setImageUrl(imageUrl);
            Product updatedProduct = productService.save(product);

            return ResponseEntity.ok(convertToDTO(updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to update product image: " + e.getMessage()));
        }
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
}

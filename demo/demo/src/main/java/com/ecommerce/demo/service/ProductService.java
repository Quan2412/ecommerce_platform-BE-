package com.ecommerce.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.repository.CategoryRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.ShopRepository;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    /**
     * Basic save method for product
     */
    @Transactional
    public Product save(Product product) {
        if (product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        validateBasicProduct(product);
        return productRepository.save(product);
    }

    
     // Find product by ID
     
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    
    // Basic product validation
     
    private void validateBasicProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
        if (product.getStockQuantity() == null || product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity must be non-negative");
        }
    }

    
     //Create a new product with image
     
    @Transactional
    public Product createProductWithImage(Product product, MultipartFile image) throws java.io.IOException {
        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(image);
                product.setImageUrl(imageUrl);
            }
            return save(product);
        } catch (IOException e) {
            log.error("Error creating product with image: {}", e.getMessage());
            throw new RuntimeException("Failed to create product with image: " + e.getMessage());
        }
    }

    /**
     * Update product image
     */
    @Transactional
    public Product updateProductImage(Long id, MultipartFile image) throws java.io.IOException {
        try {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

            if (image != null && !image.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(image);
                product.setImageUrl(imageUrl);
            }

            return productRepository.save(product);
        } catch (IOException e) {
            log.error("Error updating product image: {}", e.getMessage());
            throw new RuntimeException("Failed to update product image: " + e.getMessage());
        }
    }

    
}
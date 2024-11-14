package com.ecommerce.demo.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by shop ID
    @Query("""
        SELECT p FROM Product p 
        WHERE p.shop.id = :shopId
        ORDER BY p.createdAt DESC
    """)
    List<Product> findByShopId(@Param("shopId") Long shopId);

    // Find products by category ID
    @Query("""
        SELECT p FROM Product p 
        WHERE p.category.id = :categoryId
        ORDER BY p.createdAt DESC
    """)
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    // Search products by name (case-insensitive)
    @Query("""
        SELECT p FROM Product p 
        WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY p.createdAt DESC
    """)
    List<Product> findByProductNameContainingIgnoreCase(@Param("keyword") String keyword);

    // Find products with low stock (less than specified quantity)
    @Query("""
        SELECT p FROM Product p 
        WHERE p.stockQuantity <= :threshold
        ORDER BY p.stockQuantity ASC
    """)
    List<Product> findProductsWithLowStock(@Param("threshold") Integer threshold);

    // Find products by price range
    @Query("""
        SELECT p FROM Product p 
        WHERE p.price BETWEEN :minPrice AND :maxPrice
        ORDER BY p.price ASC
    """)
    List<Product> findByPriceRange(
        @Param("minPrice") Double minPrice, 
        @Param("maxPrice") Double maxPrice
    );

    // Get best selling products
    @Query(value = """
        SELECT p.*, COUNT(oi.ProductID) as orderCount 
        FROM Product p 
        LEFT JOIN Order_Item oi ON p.ProductID = oi.ProductID 
        GROUP BY p.ProductID 
        ORDER BY orderCount DESC 
        LIMIT :limit
        """, nativeQuery = true)
    List<Product> findBestSellingProducts(@Param("limit") int limit);

    // Get latest products
    @Query("""
        SELECT p FROM Product p 
        WHERE p.createdAt >= :since 
        ORDER BY p.createdAt DESC
    """)
    List<Product> findLatestProducts(@Param("since") LocalDateTime since);

    // Get products by type with pagination
    @Query("""
        SELECT p FROM Product p 
        WHERE p.productType = :type 
        ORDER BY p.createdAt DESC
    """)
    Page<Product> findByProductType(
        @Param("type") String type, 
        Pageable pageable
    );

    // Search products with multiple criteria
    @Query("""
        SELECT p FROM Product p 
        WHERE (:categoryId IS NULL OR p.category.id = :categoryId)
        AND (:shopId IS NULL OR p.shop.id = :shopId)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        AND (:keyword IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')))
        ORDER BY 
        CASE 
            WHEN :sortBy = 'price_asc' THEN p.price 
            WHEN :sortBy = 'price_desc' THEN p.price 
            ELSE p.createdAt 
        END
    """)
    Page<Product> searchProducts(
        @Param("categoryId") Long categoryId,
        @Param("shopId") Long shopId,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("keyword") String keyword,
        @Param("sortBy") String sortBy,
        Pageable pageable
    );

    // Get product statistics for a shop
    @Query(value = """
        SELECT 
            COUNT(*) as totalProducts,
            AVG(p.Price) as averagePrice,
            SUM(p.StockQuantity) as totalStock,
            MIN(p.Price) as lowestPrice,
            MAX(p.Price) as highestPrice,
            COUNT(DISTINCT p.CategoryID) as categoriesCount
        FROM Product p
        WHERE p.ShopID = :shopId
        """, nativeQuery = true)
    Map<String, Object> getShopProductStatistics(@Param("shopId") Long shopId);

    // Find similar products (same category, similar price range)
    @Query("""
        SELECT p FROM Product p 
        WHERE p.category.id = :categoryId 
        AND p.id != :productId 
        AND p.price BETWEEN :minPrice AND :maxPrice 
        ORDER BY p.createdAt DESC
    """)
    List<Product> findSimilarProducts(
        @Param("productId") Long productId,
        @Param("categoryId") Long categoryId,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        Pageable pageable
    );

    // Get trending products (most ordered in last X days)
    @Query(value = """
        SELECT p.* FROM Product p 
        JOIN Order_Item oi ON p.ProductID = oi.ProductID 
        JOIN [Order] o ON oi.OrderID = o.OrderID 
        WHERE o.OrderDate >= :since 
        GROUP BY p.ProductID 
        ORDER BY COUNT(oi.OrderID) DESC 
        LIMIT :limit
        """, nativeQuery = true)
    List<Product> findTrendingProducts(
        @Param("since") LocalDateTime since, 
        @Param("limit") int limit
    );

    
}

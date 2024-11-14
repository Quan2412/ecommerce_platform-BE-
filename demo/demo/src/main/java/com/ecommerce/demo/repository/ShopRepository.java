package com.ecommerce.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    // Basic find by ID with products
    @Query("""
        SELECT DISTINCT s FROM Shop s 
        LEFT JOIN FETCH s.products 
        WHERE s.shopId = :id
    """)
    Optional<Shop> findByIdWithProducts(@Param("id") Long id);

    // Find by seller ID
    @Query("""
        SELECT s FROM Shop s 
        WHERE s.seller.userId = :sellerId
    """)
    Optional<Shop> findBySellerId(@Param("sellerId") Long sellerId);

    // Search shops by name or description
    @Query("""
        SELECT s FROM Shop s 
        WHERE LOWER(s.shopName) LIKE LOWER(CONCAT('%', :keyword, '%')) 
        OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Shop> searchShops(@Param("keyword") String keyword, Pageable pageable);

    // Get shop with product count
    @Query("""
        SELECT s, COUNT(p) as productCount 
        FROM Shop s 
        LEFT JOIN s.products p 
        WHERE s.shopId = :shopId 
        GROUP BY s
    """)
    Object[] getShopWithProductCount(@Param("shopId") Long shopId);

    // Find shops with minimum number of products
    @Query("""
        SELECT s FROM Shop s 
        LEFT JOIN s.products p 
        GROUP BY s 
        HAVING COUNT(p) >= :minProducts
    """)
    List<Shop> findShopsWithMinProducts(@Param("minProducts") int minProducts);

    // Find shops created between dates
    @Query("""
        SELECT s FROM Shop s 
        WHERE s.createdAt BETWEEN :startDate AND :endDate 
        ORDER BY s.createdAt DESC
    """)
    List<Shop> findShopsByCreatedAtBetween(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );

    // Find shops by seller and date range
    @Query("""
        SELECT s FROM Shop s 
        WHERE s.seller.userId = :sellerId 
        AND s.createdAt BETWEEN :startDate AND :endDate 
        ORDER BY s.createdAt DESC
    """)
    List<Shop> findShopsBySellerAndDateRange(
        @Param("sellerId") Long sellerId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // Check if shop name exists
    @Query("SELECT COUNT(s) > 0 FROM Shop s WHERE LOWER(s.shopName) = LOWER(:shopName)")
    boolean existsByShopName(@Param("shopName") String shopName);

    // Find shops with products in specific category
    @Query("""
        SELECT DISTINCT s FROM Shop s 
        JOIN s.products p 
        WHERE p.category.categoriesId = :categoryId
    """)
    List<Shop> findShopsWithProductsInCategory(@Param("categoryId") Long categoryId);

    // Get shop statistics
    @Query("""
        SELECT 
            s.shopId as shopId,
            s.shopName as shopName,
            COUNT(DISTINCT p) as totalProducts,
            AVG(p.price) as averagePrice,
            MAX(p.price) as maxPrice,
            MIN(p.price) as minPrice
        FROM Shop s 
        LEFT JOIN s.products p 
        WHERE s.shopId = :shopId 
        GROUP BY s.shopId, s.shopName
    """)
    Object[] getShopStatistics(@Param("shopId") Long shopId);

    // Find most active shops (by product count)
    @Query("""
        SELECT s, COUNT(p) as productCount 
        FROM Shop s 
        LEFT JOIN s.products p 
        GROUP BY s 
        ORDER BY COUNT(p) DESC
    """)
    List<Object[]> findMostActiveShops(Pageable pageable);

    // Custom search with multiple criteria
    @Query("""
        SELECT DISTINCT s FROM Shop s 
        LEFT JOIN s.products p 
        WHERE (:keyword IS NULL OR (
            LOWER(s.shopName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR 
            LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ))
        AND (:categoryId IS NULL OR p.category.categoriesId = :categoryId)
        AND (:minProducts IS NULL OR (
            SELECT COUNT(p2) FROM Product p2 WHERE p2.shop = s
        ) >= :minProducts)
        ORDER BY s.createdAt DESC
    """)
    Page<Shop> searchShopsWithCriteria(
        @Param("keyword") String keyword,
        @Param("categoryId") Long categoryId,
        @Param("minProducts") Integer minProducts,
        Pageable pageable
    );
}
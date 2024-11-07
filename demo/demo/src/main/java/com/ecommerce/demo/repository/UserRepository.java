package com.ecommerce.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.dto.UserDashboardDTO;
import com.ecommerce.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("""
    SELECT NEW com.ecommerce.demo.dto.UserDashboardDTO(
        u.username,
        COUNT(DISTINCT o.orderId) as totalPurchases,
        (SELECT oi.product.productName 
         FROM OrderItem oi 
         JOIN oi.order o2 
         WHERE o2.user = u 
         GROUP BY oi.product.productName 
         ORDER BY COUNT(oi) DESC 
         FETCH FIRST 1 ROW ONLY) as mostBoughtProduct,
        (SELECT p.productName 
         FROM Product p 
         WHERE p.user = u 
         GROUP BY p.productName 
         ORDER BY COUNT(p) DESC 
         FETCH FIRST 1 ROW ONLY) as mostSoldProduct,
        COALESCE(SUM(o.totalAmount), 0) as totalRevenue)
    FROM User u
    LEFT JOIN Order o ON o.user = u
    GROUP BY u.userId, u.username
    ORDER BY totalRevenue DESC
""")
List<UserDashboardDTO> findUserDashboardStats();
}
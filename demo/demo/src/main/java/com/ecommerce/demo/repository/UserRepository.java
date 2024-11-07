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

     @Query("""
        SELECT NEW com.ecommerce.demo.dto.UserDashboardDTO(
            u.username,
            COUNT(o.id) as totalPurchases,
            p1.name as mostBoughtProduct,
            p2.name as mostSoldProduct,
            SUM(o.totalAmount) as totalRevenue)
        FROM User u
        LEFT JOIN Order o ON u.id = o.userId
        LEFT JOIN (
            SELECT op.product.name, COUNT(op) as buyCount
            FROM OrderProduct op
            GROUP BY op.product.name
            ORDER BY buyCount DESC
            LIMIT 1
        ) p1
        LEFT JOIN (
            SELECT sp.product.name, COUNT(sp) as sellCount
            FROM SellerProduct sp
            GROUP BY sp.product.name
            ORDER BY sellCount DESC
            LIMIT 1
        ) p2
        GROUP BY u.id, u.username
        ORDER BY totalRevenue DESC
    """)
    List<UserDashboardDTO> findUserDashboardStats();
}
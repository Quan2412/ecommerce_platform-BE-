package com.ecommerce.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


    @Query(value = """
        SELECT u.user_id as userId, u.username, 
               COUNT(o.orderid) as totalOrders,
               COALESCE(SUM(o.total_amount), 0) as totalSpent
        FROM [user] u
        LEFT JOIN [order] o ON u.user_id = o.userid
        GROUP BY u.user_id, u.username
        ORDER BY totalSpent DESC
        LIMIT 10
        """, nativeQuery = true)
List<Object[]> findUserDashboardStats();
}
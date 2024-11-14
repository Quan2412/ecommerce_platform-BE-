package com.ecommerce.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserStatDTO {
    private Long userId;
    private String username;
    private Integer totalOrders;
    private BigDecimal totalSpent;
}
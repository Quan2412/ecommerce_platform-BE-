package com.ecommerce.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.demo.dto.DashboardDTO;
import com.ecommerce.demo.dto.UserStatDTO;
import com.ecommerce.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {
    private final UserRepository userRepository;

    public DashboardDTO getDashboardStats() {
        DashboardDTO dashboard = new DashboardDTO();
        
        // Get top users
        List<Object[]> userStats = userRepository.findUserDashboardStats();
        List<UserStatDTO> topUsers = new ArrayList<>();
        
        for (Object[] stat : userStats) {
            UserStatDTO userStat = new UserStatDTO();
            userStat.setUserId(((Number) stat[0]).longValue());
            userStat.setUsername((String) stat[1]);
            userStat.setTotalOrders(((Number) stat[2]).intValue());
            userStat.setTotalSpent(new BigDecimal(String.valueOf(stat[3])));
            topUsers.add(userStat);
        }
        
        dashboard.setTopUsers(topUsers);
        
        
        Map<String, Object> statistics = Map.of(
            "totalUsers", userRepository.count(),
            "topSpenders", topUsers.stream()
                .limit(5)
                .map(UserStatDTO::getUsername)
                .toList()
        );
        dashboard.setStatistics(statistics);
        
        return dashboard;
    }
}

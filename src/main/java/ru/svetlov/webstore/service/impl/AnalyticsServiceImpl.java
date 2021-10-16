package ru.svetlov.webstore.service.impl;

import org.springframework.stereotype.Service;
import ru.svetlov.webstore.service.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Override
    public Integer userItemsOrderedByProduct(Long userId, Long productId) {
        return userId != null && productId != null && userId > 0 && productId > 0 ? 1 : 0;
    }
}

package ru.svetlov.webstore.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.core.repository.AnalyticsRepository;
import ru.svetlov.webstore.core.service.AnalyticsService;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final AnalyticsRepository analytics;

    @Override
    public Integer userItemsOrderedByProduct(Long userId, Long productId) {
        return userId != null && productId != null ? analytics.getOrderItemQuantity(userId, productId) : 0;
    }
}

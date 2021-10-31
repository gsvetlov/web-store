package ru.svetlov.webstore.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.core.service.AnalyticsService;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    @Override
    public Integer userItemsOrderedByProduct(Long userId, Long productId) {
        return userId != null && productId != null ? 1 : 0;
    }
}

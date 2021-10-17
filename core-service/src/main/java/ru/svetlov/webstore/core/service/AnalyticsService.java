package ru.svetlov.webstore.core.service;

public interface AnalyticsService {
    Integer userItemsOrderedByProduct(Long userId, Long productId);
}

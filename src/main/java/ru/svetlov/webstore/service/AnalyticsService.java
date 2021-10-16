package ru.svetlov.webstore.service;

public interface AnalyticsService {
    Integer userItemsOrderedByProduct(Long userId, Long productId);
}

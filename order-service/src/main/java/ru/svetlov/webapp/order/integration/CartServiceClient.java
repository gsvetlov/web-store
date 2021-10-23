package ru.svetlov.webapp.order.integration;

import ru.svetlov.webstore.api.dtos.CartDto;

public interface CartServiceClient {
    CartDto getCart(Long userId);
    void clear(String cartId);
}

package ru.svetlov.webapp.order.integration.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.svetlov.webapp.order.integration.CartServiceClient;
import ru.svetlov.webstore.api.dtos.CartDto;

@Service
@RequiredArgsConstructor
public class CartServiceClientImpl implements CartServiceClient {
    private final WebClient webClient;
    @Value("${integration.service.cart.uri}")
    private String cartServiceUri;

    @Override
    public CartDto getCart(Long userId) {
        return webClient.getForEntity(cartServiceUri, CartDto.class, userId).getBody();
    }

    @Override
    public void clear(String cartId) {
        // TODO: fix CartService API
    }
}

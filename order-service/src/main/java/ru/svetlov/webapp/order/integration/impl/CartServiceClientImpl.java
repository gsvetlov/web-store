package ru.svetlov.webapp.order.integration.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.svetlov.webapp.order.integration.CartServiceClient;
import ru.svetlov.webstore.api.dtos.CartDto;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CartServiceClientImpl implements CartServiceClient {
    @Qualifier("cartService")
    private final WebClient webClient;

    @Override
    public CartDto getCart(Long userId) {
        CartDto cartDto = webClient.get()
                .uri("/cart/"+userId)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block(Duration.ofMillis(1000L));
        return cartDto;
    }

    @Override
    public void clear(String cartId) {
        webClient.get()
                .uri("/" + cartId + "/clear")
                .retrieve()
                .toBodilessEntity();
    }
}

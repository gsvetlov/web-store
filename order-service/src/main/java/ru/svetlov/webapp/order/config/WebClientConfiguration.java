package ru.svetlov.webapp.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Value("${integration.services.cart.url}")
    private String cartServiceUrl;

    @Bean("cartService")
    public WebClient getCartServiceClient() {
        return WebClient.builder()
                .baseUrl(cartServiceUrl)
                .clientConnector(new ReactorClientHttpConnector())
                .build();
    }
}

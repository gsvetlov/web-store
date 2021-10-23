package ru.svetlov.webapp.order.domain;

import java.util.stream.Stream;

public enum OrderStatus {
    NEW(100), APPROVED(200), PAID(300), PREPARING(400), DELIVERING(500), COMPLETE(600), CANCELED(700);

    private int status;

    private OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static OrderStatus of(int status) {
        return Stream.of(OrderStatus.values())
                .filter(o -> o.status == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

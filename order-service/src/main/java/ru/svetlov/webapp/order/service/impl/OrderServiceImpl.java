package ru.svetlov.webapp.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.svetlov.webapp.order.domain.Order;
import ru.svetlov.webapp.order.domain.OrderDetails;
import ru.svetlov.webapp.order.domain.OrderItem;
import ru.svetlov.webapp.order.integration.CartServiceClient;
import ru.svetlov.webapp.order.repository.OrderRepository;
import ru.svetlov.webapp.order.service.OrderService;
import ru.svetlov.webstore.api.dtos.CartDto;
import ru.svetlov.webstore.api.dtos.OrderDetailsDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceClient cartServiceClient;

    @Override
    public Optional<Order> getOrderById(Long orderId, Long userId) {
        Optional<Order> order = orderRepository.findOrderById(orderId);
        return order.isPresent() && userId.equals(order.get().getUserId())
                ? order : Optional.empty();
    }

    @Override
    public Order createOrder(Long userId, OrderDetailsDto details) {
        CartDto cart = cartServiceClient.getCart(userId);
        List<OrderItem> orderItems = mapCartToOrderItems(cart);
        Order order = create(userId, details, orderItems);
        cartServiceClient.clear(cart.getCartId());
        return order;
    }

    @Transactional
    private Order create(Long userId, OrderDetailsDto details, List<OrderItem> orderItems) {
        Order order = new Order(userId);
        order.setOrderItems(orderItems);
        order.setDetails(OrderDetails.of(details.getAddress(), details.getPhone()));
        orderRepository.save(order);
        return order;
    }

    private List<OrderItem> mapCartToOrderItems(CartDto cart) {
        return cart.getContents().stream()
                .map(ci -> OrderItem.of(
                        ci.getProductId(),
                        ci.getQuantity(),
                        ci.getProductPrice(),
                        ci.getItemSum()))
                .collect(Collectors.toList());
    }
}

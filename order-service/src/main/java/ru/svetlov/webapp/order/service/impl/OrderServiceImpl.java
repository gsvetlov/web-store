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
import ru.svetlov.webstore.api.dtos.CartItemDto;
import ru.svetlov.webstore.api.dtos.OrderDetailsDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceClient cartServiceClient;
//    private final ProductService productService;

    @Override
    public Optional<Order> getOrderById(Long orderId, Long userId) {
        Optional<Order> order = orderRepository.findOrderById(orderId);
        //TODO: проверка, что заказ принадлежит пользователю, иначе Optional.empty()
        return order;
    }

    @Transactional
    @Override
    public Order createOrder(Long userId, OrderDetailsDto details) {
        CartDto cart = cartServiceClient.getCart(userId);
        Order order = new Order(userId);
        order.setOrderItems(mapCartToOrderItems(cart));
        order.setDetails(OrderDetails.of(details.getAddress(), details.getPhone()));
        orderRepository.save(order);
        cartServiceClient.clear(cart.getCartId()); // TODO: fix cart clear
        return order;
    }

    private List<OrderItem> mapCartToOrderItems(CartDto cart) {
        Map<Long, Long> products = mapCartIdsToProducts(cart);
        return cart.getContents().stream()
                .map(ci -> OrderItem.of(
                        products.get(ci.getProductId()),
                        ci.getQuantity(),
                        ci.getProductPrice(),
                        ci.getItemSum()))
                .collect(Collectors.toList());
    }

    private Map<Long, Long> mapCartIdsToProducts(CartDto cart) {
        List<Long> ids = cart.getContents().stream().map(CartItemDto::getProductId).collect(Collectors.toList());
        return Collections.EMPTY_MAP; // TODO: fix product-service integration
        // productService.getAllByIdIn(ids).stream().collect(Collectors.toMap(Long::getLong, Function.identity()));
    }
}

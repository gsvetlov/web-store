package ru.svetlov.webstore.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.svetlov.webstore.core.domain.*;
import ru.svetlov.webstore.api.dto.CartItemDto;
import ru.svetlov.webstore.api.dto.OrderDetailsDto;
import ru.svetlov.webstore.core.repository.OrderRepository;
import ru.svetlov.webstore.core.service.OrderService;
import ru.svetlov.webstore.core.service.ProductService;
import ru.svetlov.webstore.core.util.cart.Cart;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    public Optional<Order> getOrderById(Long orderId, User user) {
        Optional<Order> order = orderRepository.findOrderById(orderId);
        //TODO: проверка, что заказ принадлежит пользователю, иначе Optional.empty()
        return order;
    }

    @Transactional
    @Override
    public Order createOrder(User user, Cart cart, OrderDetailsDto details) {
        Order order = new Order(user);
        order.setOrderItems(mapCartToOrderItems(cart));
        order.setDetails(OrderDetails.of(details.getAddress(), details.getPhone()));
        orderRepository.save(order);
        cart.clear();
        return order;
    }

    private List<OrderItem> mapCartToOrderItems(Cart cart) {
        Map<Long, Product> products = mapCartIdsToProducts(cart);
        return cart.getContents().stream()
                .map(ci -> OrderItem.of(
                        products.get(ci.getProductId()),
                        ci.getQuantity(),
                        ci.getProductPrice(),
                        ci.getItemSum()))
                .collect(Collectors.toList());
    }

    private Map<Long, Product> mapCartIdsToProducts(Cart cart) {
        List<Long> ids = cart.getContents().stream().map(CartItemDto::getProductId).collect(Collectors.toList());
        return productService.getAllByIdIn(ids).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }
}

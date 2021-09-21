package ru.svetlov.webstore.util.cart.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.util.cart.Cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
@RequiredArgsConstructor
public class CartImpl implements Cart, Serializable {
    private static final int INCREMENT_VALUE = 1;
    private static final int DECREMENT_VALUE = -1;

    private final List<CartItemDto> contents;

    @Id
    private final String id;

    public CartImpl() {
        this.id = "";
        this.contents = new ArrayList<>();
    }

    public CartImpl(String id) {
        this.id = id;
        this.contents = new ArrayList<>();
    }

    public CartImpl(String id, List<CartItemDto> contents) {
        this.id = id;
        this.contents = contents;
    }

    @JsonIgnore
    @Override
    public BigDecimal getTotal() {
        return contents.stream().map(CartItemDto::getItemSum).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean incrementItem(Long productId) {
        return changeItem(productId, INCREMENT_VALUE);
    }

    @Override
    public void decrementItem(Long productId) {
        changeItem(productId, DECREMENT_VALUE);
    }

    private boolean changeItem(Long productId, int delta) {
        if (contents.stream().noneMatch(i -> i.getProductId().equals(productId))) {
            return false;
        }
        CartItemDto item = findByProductId(productId);
        item.changeQuantity(delta);
        if (item.getQuantity() <= 0) {
            removeItem(productId);
        }
        return true;
    }

    private CartItemDto findByProductId(Long productId) {
        return contents.stream().filter(i -> i.getProductId().equals(productId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Product id: " + productId + " not found"));
    }

    @Override
    public void addItem(Product product) {
        contents.add(new CartItemDto(product));
    }

    @Override
    public void removeItem(Long productId) {
        contents.remove(findByProductId(productId));
    }

    @Override
    public void clear() {
        contents.clear();
    }

    @Override
    public Collection<CartItemDto> getContents() {
        return Collections.unmodifiableCollection(contents);
    }

    @Override
    public void merge(Cart other) {
        Collection<CartItemDto> otherContents = other.getContents();
        Collection<CartItemDto> mergeList = getListToMerge(otherContents);
        mergeContents(mergeList);
        contents.addAll(distinctNew(otherContents, mergeList));
    }

    private Collection<CartItemDto> getListToMerge(Collection<CartItemDto> other) {
        return other.stream()
                .filter(oi -> contents.stream().anyMatch(ci -> ci.getProductId().equals(oi.getProductId())))
                .collect(Collectors.toList());
    }

    private void mergeContents(Collection<CartItemDto> mergeList) {
        mergeList.forEach(source -> {
            CartItemDto dest = findByProductId(source.getProductId());
            dest.changePrice(dest.getProductPrice().max(source.getProductPrice()));
            dest.changeQuantity(source.getQuantity());
        });
    }

    private Collection<CartItemDto> distinctNew(Collection<CartItemDto> other, Collection<CartItemDto> mergeList) {
        return other.stream()
                .filter(item -> mergeList.stream().noneMatch(mi -> mi.getProductId().equals(item.getProductId())))
                .collect(Collectors.toList());
    }

}

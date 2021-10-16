package ru.svetlov.webstore.service;

import ru.svetlov.webstore.domain.Comment;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.domain.User;

import java.util.Collection;

public interface CommentService {
    Collection<Comment> getByProduct(Product product);

    Collection<Comment> getByProduct(Long productId);

    Comment add(User user, Product product, String content);

    boolean canUpdate(Long userId, Long productId);
}

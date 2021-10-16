package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Comment;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.exception.BadRequestException;
import ru.svetlov.webstore.repository.CommentRepository;
import ru.svetlov.webstore.service.AnalyticsService;
import ru.svetlov.webstore.service.CommentService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final AnalyticsService analytics;

    @Override
    public Collection<Comment> getByProduct(Product product) {
        return getByProduct(product.getId());
    }

    @Override
    public Collection<Comment> getByProduct(Long productId) {
        return repository.findAllByProduct(productId);
    }

    @Override
    public Comment add(User user, Product product, String content) {
        if (!canUpdate(user.getId(), product.getId())) {
            throw new BadRequestException("User " + user.getUsername() + " can't leave comments");
        }
        Comment comment = new Comment(content, user, product);
        repository.save(comment);
        return comment;
    }

    @Override
    public boolean canUpdate(Long userId, Long productId) {
        return userId != null && analytics.userItemsOrderedByProduct(userId, productId) > 0;
    }
}

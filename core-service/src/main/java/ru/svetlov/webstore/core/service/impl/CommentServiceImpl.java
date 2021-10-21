package ru.svetlov.webstore.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.api.exceptions.BadRequestException;
import ru.svetlov.webstore.api.exceptions.ResourceNotFoundException;
import ru.svetlov.webstore.core.domain.Comment;
import ru.svetlov.webstore.core.domain.Product;
import ru.svetlov.webstore.core.domain.User;
import ru.svetlov.webstore.core.repository.CommentRepository;
import ru.svetlov.webstore.core.service.AnalyticsService;
import ru.svetlov.webstore.core.service.CommentService;
import ru.svetlov.webstore.core.service.ProductService;
import ru.svetlov.webstore.core.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final AnalyticsService analytics;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public Collection<Comment> getAllByProduct(Long productId) {
        return repository.findAllByProduct(productId);
    }

    @Override
    public Comment add(String username, Long productId, String content) {
        User user = userService.findUserByName(username).orElseThrow(() -> new BadRequestException(username + ": user not found"));
        Product product = productService.getById(productId).orElseThrow(() -> new ResourceNotFoundException(productId + ": product not found"));

        if (!canLeaveComment(user.getId(), productId)) {
            throw new BadRequestException("User " + user.getUsername() + " can't leave comments");
        }

        Comment comment = new Comment(content, user, product);
        repository.save(comment);

        return comment;
    }

    @Override
    public boolean canLeaveComment(String username, Long productId) {
        Optional<User> user = userService.findUserByName(username);
        return user.isPresent() && productId != null && canLeaveComment(user.get().getId(), productId);
    }

    private boolean canLeaveComment(Long uid, Long pid) {
        return analytics.userItemsOrderedByProduct(uid, pid) > 0;
    }
}

package ru.svetlov.webstore.service;

import ru.svetlov.webstore.domain.Comment;

import java.util.Collection;

public interface CommentService {

    Collection<Comment> getAllByProduct(Long productId);

    Comment add(String username, Long productId, String content);

    boolean canLeaveComment(String username, Long productId);
}

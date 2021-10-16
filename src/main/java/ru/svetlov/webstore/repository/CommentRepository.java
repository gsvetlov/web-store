package ru.svetlov.webstore.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.svetlov.webstore.domain.Comment;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(value = "comment-with-user")
    Collection<Comment> findAllByProduct(Long productId);
}

package ru.svetlov.webstore.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.core.domain.Comment;
import ru.svetlov.webstore.core.domain.Product;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user"})
    @Query("from Comment c where c.product.id = :pid")
    Collection<Comment> findAllByProduct(@Param("pid")Long productId);

    @EntityGraph(attributePaths = {"user"})
    Collection<Comment> findAllByProduct(Product product);
}

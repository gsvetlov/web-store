package ru.svetlov.webstore.core.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "comments", indexes = @Index(name = "idx_comments_product", columnList = "product_id"))
@NamedEntityGraph(name = "comment-with-users", attributeNodes = {@NamedAttributeNode(value = "user")})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content")
    @Length(max = 256, message = "maximum comment length is 256 characters")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime modified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Comment(String content, User user, Product product) {
        this.content = content;
        this.user = user;
        this.product = product;
    }

    private Comment(Long id, String content, User user, Product product, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.product = product;
        this.created = created;
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }
}

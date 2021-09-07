package ru.svetlov.webstore.domain;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@NamedEntityGraph(name = "user-with-roles", attributeNodes = @NamedAttributeNode(value = "roles"))
@NamedEntityGraph(name = "user-with-info", attributeNodes = @NamedAttributeNode(value = "userInfo"))

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    @NotNull
    @Length(min = 3, max = 127, message = "Valid username length is 3 to 127 characters")
    private String username;

    @Column(name = "password")
    @NotNull
    @Length(min = 3, max = 127, message = "Valid password length is 3 to 127 characters")
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id")
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<SecurityRole> roles;

    @Column(name = "created_on")
    @CreatedDate
    private java.sql.Date createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime modifiedOn;

    @Version
    private java.sql.Timestamp version;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

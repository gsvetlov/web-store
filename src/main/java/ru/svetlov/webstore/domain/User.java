package ru.svetlov.webstore.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
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

    @OneToOne
    @JoinColumn(name = "info_id")
    private UserInfo userInfo;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<SecurityRole> roles;

}

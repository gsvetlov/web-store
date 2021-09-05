package ru.svetlov.webstore.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Table(name = "roles")
@Entity
public class SecurityRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role")
    @NotNull
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<SecurityPermission> permissions;

}
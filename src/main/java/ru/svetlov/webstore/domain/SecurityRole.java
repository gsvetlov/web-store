package ru.svetlov.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@NamedEntityGraph(name = "role-with-permissions", attributeNodes = @NamedAttributeNode(value = "permissions"))

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "roles")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SecurityRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role")
    @NotNull
    private String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<SecurityPermission> permissions;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime modified;
}
package ru.svetlov.webstore.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@NamedEntityGraph(name = "user-with-roles", attributeNodes = @NamedAttributeNode(value = "roles"))
@NamedEntityGraph(name = "user-with-info-and-roles", attributeNodes = {
        @NamedAttributeNode(value = "userInfo"),
        @NamedAttributeNode(value = "roles")
})

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Transient
    public final static User UNKNOWN = new User("", null);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<SecurityRole> roles;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime modified;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Collection<String> getRolesAsStrings() {
        return roles.stream().map(SecurityRole::getRole).collect(Collectors.toList());
    }

    public Collection<String> getPermissionsAsStrings() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream().map(SecurityPermission::getPermission))
                .distinct()
                .collect(Collectors.toList());
    }
}

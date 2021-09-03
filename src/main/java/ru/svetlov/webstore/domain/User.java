package ru.svetlov.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @UniqueElements
    @Length(max = 255, message = "Max field length: 255")
    private String username;

    @NotNull
    @Length(max = 255, message = "Max field length: 255")
    private String password;

    private boolean enabled;

    @NotNull
    @Length(max = 255, message = "Max field length: 255")
    private String name;

    @Length(max = 255, message = "Max field length: 255")
    private String email;

    @Min(value = 12, message = "Min user age: 12")
    private Integer age;

    @ToString.Exclude
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}

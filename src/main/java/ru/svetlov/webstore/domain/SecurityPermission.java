package ru.svetlov.webstore.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "permissions")
public class SecurityPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "permission")
    @NotNull
    @Length(min = 3, max = 255, message = "Valid permission length is 3 to 255 characters")
    private String permission;
}
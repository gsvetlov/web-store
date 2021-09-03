package ru.svetlov.webstore.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "permissions")
@Entity
public class UserPermission {
    @Id
    @Column(name = "permission_id")
    private Integer id;

    @Column(name = "permission")
    @NotNull
    @UniqueElements
    @Length(max = 255, message = "Max field length: 255")
    private String permission;

    @Override
    public String toString() {
        return permission;
    }
}
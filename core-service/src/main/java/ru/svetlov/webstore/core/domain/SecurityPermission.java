package ru.svetlov.webstore.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "permissions")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SecurityPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "permission")
    @NotNull
    @Length(min = 3, max = 255, message = "Valid permission length is 3 to 255 characters")
    private String permission;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime modified;
}
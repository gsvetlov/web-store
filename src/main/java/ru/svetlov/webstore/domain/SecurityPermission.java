package ru.svetlov.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "permission")
    @NotNull
    @Length(min = 3, max = 255, message = "Valid permission length is 3 to 255 characters")
    private String permission;

    @Column(name = "created")
    @NotNull
    @CreatedDate
    private LocalDateTime timeCreated;

    @Column(name = "updated")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Version
    private LocalDateTime timestamp;
}
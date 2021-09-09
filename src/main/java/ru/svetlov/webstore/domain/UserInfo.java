package ru.svetlov.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    private Long id;

    @Column(name = "first_name")
    @Length(min = 3, max = 127, message = "Valid first name length is 3 to 127 characters")
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 3, max = 127, message = "Valid last name length is 3 to 127 characters")
    private String lastName;

    @Column(name = "mid_name")
    @Length(max = 127, message = "Valid username length is 3 to 127 characters")
    private String middleName;

    @Column(name = "email")
    @Email(message = "Not valid email address")
    private String email;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime modified;

    public UserInfo(String firstName, String lastName, String middleName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
    }
}
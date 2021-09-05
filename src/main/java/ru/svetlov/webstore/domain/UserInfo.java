package ru.svetlov.webstore.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "info_id")
    private Long id;

    @Column(name = "first_name")
    @Length(min = 3, max = 127, message = "Valid first name length is 3 to 127 characters")
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 3, max = 127, message = "Valid last name length is 3 to 127 characters")
    private String lastName;

    @Column(name = "mid_name")
    @Length(min = 3, max = 127, message = "Valid username length is 3 to 127 characters")
    private String middleName;

    @Column(name = "email")
    @Email(message = "Not valid email address")
    private String email;
}
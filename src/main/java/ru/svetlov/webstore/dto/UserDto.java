package ru.svetlov.webstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.svetlov.webstore.domain.SecurityPermission;
import ru.svetlov.webstore.domain.SecurityRole;
import ru.svetlov.webstore.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private Collection<String> roles;
    private Collection<String> permissions;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRolesAsStrings();
        this.permissions = user.getPermissionsAsStrings();
        if (user.getUserInfo() != null) {
            this.firstName = user.getUserInfo().getFirstName();
            this.lastName = user.getUserInfo().getLastName();
            this.middleName = user.getUserInfo().getMiddleName();
            this.email = user.getUserInfo().getEmail();
        }
    }
}

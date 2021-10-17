package ru.svetlov.webstore.core.util.converters;

import org.springframework.stereotype.Component;
import ru.svetlov.webstore.api.dto.UserDto;
import ru.svetlov.webstore.core.domain.User;

import java.util.Collection;

@Component
public class UserConverter {
    public UserDto map(User user) {
        Long id = user.getId();
        String username = user.getUsername();
        Collection<String> roles = user.getRolesAsStrings();
        Collection<String> permissions = user.getPermissionsAsStrings();

        String firstName = "";
        String lastName = "";
        String middleName = "";
        String email = "";
        if (user.getUserInfo() != null) {
            firstName = user.getUserInfo().getFirstName();
            lastName = user.getUserInfo().getLastName();
            middleName = user.getUserInfo().getMiddleName();
            email = user.getUserInfo().getEmail();
        }

        return new UserDto(id, username, null, firstName, lastName, middleName, email, roles, permissions);
    }
}

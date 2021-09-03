package ru.svetlov.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    @Query("select u from User u left join u.roles role left join role.directPermissions left join role.permissionGroups pg left join pg.permissions where u.username = :username")
    Optional<User> findUserWithAllRolesAndPermissionsByUsername(@Param("username") String username);
}

package ru.svetlov.webstore.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.core.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "user-with-roles")
    Optional<User> findByUsername(String username);

    @EntityGraph(value = "user-with-info-and-roles")
    Optional<User> findUserById(Long id);

    int countByUsername(String username);

    Optional<User> findUserByUsername(String username);
}

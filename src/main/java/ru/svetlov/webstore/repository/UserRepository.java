package ru.svetlov.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

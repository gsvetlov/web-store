package ru.svetlov.webstore.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.svetlov.webstore.domain.SecurityRole;

import java.util.Collection;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Integer> {
    @EntityGraph(value = "role-with-permissions")
    Collection<SecurityRole> findAllByIdIn(Collection<Integer> ids);

    @EntityGraph(value = "role-with-permissions")
    SecurityRole findByRoleIgnoreCase(String role);
}

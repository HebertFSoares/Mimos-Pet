package io.github.hebertfsoares.ms_animals.domain.repository;

import io.github.hebertfsoares.ms_animals.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
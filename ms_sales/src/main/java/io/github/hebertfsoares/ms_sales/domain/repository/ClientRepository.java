package io.github.hebertfsoares.ms_sales.domain.repository;

import io.github.hebertfsoares.ms_sales.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
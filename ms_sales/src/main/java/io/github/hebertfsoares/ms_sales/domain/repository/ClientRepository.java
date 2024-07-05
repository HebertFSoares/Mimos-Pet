package io.github.hebertfsoares.ms_sales.domain.repository;

import io.github.hebertfsoares.ms_sales.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
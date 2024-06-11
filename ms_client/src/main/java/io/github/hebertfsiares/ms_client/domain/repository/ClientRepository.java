package io.github.hebertfsiares.ms_client.domain.repository;

import io.github.hebertfsiares.ms_client.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByCpf(String cpf);
}
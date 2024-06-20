package io.github.hebertfsoares.ms_adoptions.domain.repository;

import io.github.hebertfsoares.ms_adoptions.domain.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
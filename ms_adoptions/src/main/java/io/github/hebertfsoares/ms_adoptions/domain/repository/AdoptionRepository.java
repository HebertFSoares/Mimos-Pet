package io.github.hebertfsoares.ms_adoptions.domain.repository;

import io.github.hebertfsoares.ms_adoptions.domain.entities.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
package io.github.hebertfsoares.ms_animals.domain.repository;

import io.github.hebertfsoares.ms_animals.domain.entities.Animals;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalStatus;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalsRepository extends JpaRepository<Animals, Long> {
    Optional<Animals> findBySpecies(AnimalSpecies species);

    Optional<Animals> findBySize(AnimalSize size);

    Optional<Animals> findByGender(Gender animalsGender);

    Optional<Animals> findByStatus(AnimalStatus status);
}
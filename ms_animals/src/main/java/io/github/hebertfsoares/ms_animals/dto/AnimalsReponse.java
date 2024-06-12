package io.github.hebertfsoares.ms_animals.dto;

import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalStatus;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;

import java.awt.*;

public record AnimalsReponse(Long id, String name, AnimalSpecies species, String breed, Gender gender, AnimalSize size, Integer years, Boolean Castrated, String photoUrl, String history, AnimalStatus status) {
}

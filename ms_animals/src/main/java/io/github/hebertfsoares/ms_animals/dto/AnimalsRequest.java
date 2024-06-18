package io.github.hebertfsoares.ms_animals.dto;

import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;

public record AnimalsRequest(String name, AnimalSpecies species, String breed, Gender gender, AnimalSize size, Integer years, Boolean castrated, String photoUrl, String history, Long clientId) {
}
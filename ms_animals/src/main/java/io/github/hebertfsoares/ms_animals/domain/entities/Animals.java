package io.github.hebertfsoares.ms_animals.domain.entities;

import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalStatus;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Animals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "species")
    private AnimalSpecies species;
    @Column
    private String breed;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private AnimalSize size;
    @Column
    private Integer years;
    @Column
    private Boolean castrated;
    @Column
    private String photoUrl;
    @Column
    @Lob
    private String history;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnimalStatus status = AnimalStatus.AVAILABLE;
    private Long clientId;
    private String clientName;
    private String clientCpf;

    public Animals(String name, AnimalSpecies species, String breed, Gender gender, AnimalSize size, Integer years, Boolean castrated, String photoUrl, String history) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.gender = gender;
        this.size = size;
        this.years = years;
        this.castrated = castrated;
        this.photoUrl = photoUrl;
        this.history = history;
    }
}

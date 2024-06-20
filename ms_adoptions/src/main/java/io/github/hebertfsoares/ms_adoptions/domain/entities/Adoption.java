package io.github.hebertfsoares.ms_adoptions.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter @Setter
@NoArgsConstructor
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long animalId;
    private Long clientId;
    private LocalDate adoptionDate;
    @Column(length = 500)
    private String observations;
    private String clientName;
    private String animalName;

    public Adoption(Long animalId, Long clientId, LocalDate adoptionDate, String observations, String clientName, String animalName) {
        this.animalId = animalId;
        this.clientId = clientId;
        this.adoptionDate = adoptionDate;
        this.observations = observations;
        this.clientName = clientName;
        this.animalName = animalName;
    }
}

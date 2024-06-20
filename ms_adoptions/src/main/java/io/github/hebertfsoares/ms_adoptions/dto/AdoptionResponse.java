package io.github.hebertfsoares.ms_adoptions.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AdoptionResponse {
    private Long id;
    private Long animalId;
    private Long clientId;
    private LocalDate adoptionDate;
    private String observations;
    private String animalName;
    private String clientName;
}
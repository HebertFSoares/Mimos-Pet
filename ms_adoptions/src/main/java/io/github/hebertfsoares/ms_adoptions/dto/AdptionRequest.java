package io.github.hebertfsoares.ms_adoptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdptionRequest {
    private Long AnimalId;
    private Long clientId;
    private String observations;
}

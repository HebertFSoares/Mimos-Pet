package io.github.hebertfsoares.ms_adoptions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdptionRequest {
    private Long AnimalId;
    private Long clientId;
    private String observations;
}

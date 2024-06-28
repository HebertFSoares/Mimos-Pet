package io.github.hebertfsoares.ms_adoptions.web;

import io.github.hebertfsoares.ms_adoptions.domain.service.AdoptionService;
import io.github.hebertfsoares.ms_adoptions.dto.AdoptionResponse;
import io.github.hebertfsoares.ms_adoptions.dto.AdptionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adocao")
@RequiredArgsConstructor
@Tag(name = "Adoption Controller", description = "Endpoints to manage adoption")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @PostMapping("/register")
    @Operation(summary = "Register Adoption", description = "Registers a new adoption")
    public ResponseEntity<AdoptionResponse> registerAdoption(@RequestBody AdptionRequest adoptionRequest) {
        AdoptionResponse adoptionResponse = adoptionService.adopt(adoptionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(adoptionResponse);
    }
}
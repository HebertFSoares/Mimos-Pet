package io.github.hebertfsoares.ms_adoptions.web;

import io.github.hebertfsoares.ms_adoptions.domain.service.AdoptionService;
import io.github.hebertfsoares.ms_adoptions.dto.AdoptionResponse;
import io.github.hebertfsoares.ms_adoptions.dto.AdptionRequest;
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
public class AdoptionController {

    private final AdoptionService adoptionService;

    @PostMapping("/register")
    public ResponseEntity<AdoptionResponse> registerAdoption(@RequestBody AdptionRequest adoptionRequest) {
        AdoptionResponse adoptionResponse = adoptionService.adopt(adoptionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(adoptionResponse);
    }
}
package io.github.hebertfsoares.ms_adoptions.domain.service;

import io.github.hebertfsoares.ms_adoptions.domain.entities.Adoption;
import io.github.hebertfsoares.ms_adoptions.domain.entities.Animal;
import io.github.hebertfsoares.ms_adoptions.domain.entities.Client;
import io.github.hebertfsoares.ms_adoptions.domain.repository.AdoptionRepository;
import io.github.hebertfsoares.ms_adoptions.domain.repository.AnimalRepository;
import io.github.hebertfsoares.ms_adoptions.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_adoptions.dto.AdoptionResponse;
import io.github.hebertfsoares.ms_adoptions.dto.AdptionRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private static final Logger logger = LoggerFactory.getLogger(AdoptionService.class);

    private final AdoptionRepository adoptionRepository;
    private final AnimalRepository animalRepository;
    private final ClientRepository clientRepository;

    public AdoptionResponse adopt(AdptionRequest adoptionRequest) {
        logger.info("Adoption request received: {}", adoptionRequest);
        Animal animal = animalRepository.findById(adoptionRequest.getAnimalId())
                .orElseThrow(() -> {
                    logger.error("Animal not found for ID: {}", adoptionRequest.getAnimalId());
                    return new RuntimeException("Animal not found");
                });
        Client client = clientRepository.findById(adoptionRequest.getClientId())
                .orElseThrow(() -> {
                    logger.error("Client not found for ID: {}", adoptionRequest.getClientId());
                    return new RuntimeException("Client not found");
                });

        Adoption adoption = new Adoption();
        adoption.setAnimalId(adoptionRequest.getAnimalId());
        adoption.setClientId(adoptionRequest.getClientId());
        adoption.setObservations(adoptionRequest.getObservations());
        adoption.setAnimalName(animal.getName());
        adoption.setClientName(client.getName());

        adoptionRepository.save(adoption);
        logger.info("Adoption saved: {}", adoption);

        return new AdoptionResponse(
                adoption.getId(),
                adoption.getAnimalId(),
                adoption.getClientId(),
                adoption.getAdoptionDate(),
                adoption.getObservations(),
                adoption.getAnimalName(),
                adoption.getClientName()
        );
    }
}

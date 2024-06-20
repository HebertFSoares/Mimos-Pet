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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final AnimalRepository animalRepository;
    private final ClientRepository clientRepository;

    public AdoptionResponse adopt(AdptionRequest adoptionRequest) {
        Animal animal = animalRepository.findById(adoptionRequest.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        Client client = clientRepository.findById(adoptionRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Adoption adoption = new Adoption();
        adoption.setAnimalId(adoptionRequest.getAnimalId());
        adoption.setClientId(adoptionRequest.getClientId());
        adoption.setObservations(adoptionRequest.getObservations());
        adoption.setAnimalName(animal.getName());
        adoption.setClientName(client.getName());

        adoptionRepository.save(adoption);

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

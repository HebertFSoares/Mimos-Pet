package io.github.hebertfsoares.ms_animals.domain.service;

import io.github.hebertfsoares.ms_animals.domain.entities.Animals;
import io.github.hebertfsoares.ms_animals.domain.entities.Client;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalStatus;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;
import io.github.hebertfsoares.ms_animals.domain.repository.AnimalsRepository;
import io.github.hebertfsoares.ms_animals.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_animals.dto.AnimalsReponse;
import io.github.hebertfsoares.ms_animals.dto.AnimalsRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalsService {

    private final AnimalsRepository animalsRepository;
    private final ClientRepository clientRepository;

    public AnimalsReponse saveAnimal(AnimalsRequest animalsRequest) {
        Client client = clientRepository.findById(animalsRequest.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Animals animal = new Animals();
        animal.setName(animalsRequest.name());
        animal.setSpecies(animalsRequest.species());
        animal.setBreed(animalsRequest.breed());
        animal.setGender(animalsRequest.gender());
        animal.setSize(animalsRequest.size());
        animal.setYears(animalsRequest.years());
        animal.setCastrated(animalsRequest.castrated());
        animal.setPhotoUrl(animalsRequest.photoUrl());
        animal.setHistory(animalsRequest.history());
        animal.setClientId(client.getId());
        animal.setClientName(client.getName());
        animal.setClientCpf(client.getCpf());

        animalsRepository.save(animal);

        return new AnimalsReponse(
                animal.getId(),
                animal.getName(),
                animal.getSpecies(),
                animal.getBreed(),
                animal.getGender(),
                animal.getSize(),
                animal.getYears(),
                animal.getCastrated(),
                animal.getPhotoUrl(),
                animal.getHistory(),
                animal.getStatus(),
                animal.getClientId(),
                animal.getClientCpf(),
                animal.getClientName()
        );
    }

    public AnimalsReponse getAnimalsId(Long id){
        Animals animals = animalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animals not found"));
        return new AnimalsReponse(
                animals.getId(),
                animals.getName(),
                animals.getSpecies(),
                animals.getBreed(),
                animals.getGender(),
                animals.getSize(),
                animals.getYears(),
                animals.getCastrated(),
                animals.getPhotoUrl(),
                animals.getHistory(),
                animals.getStatus(),
                animals.getClientId(),
                animals.getClientCpf(),
                animals.getClientName()
        );
    }

    public AnimalsReponse getAnimalsSpecies(AnimalSpecies species){
        Animals animals = animalsRepository.findBySpecies(species)
                .orElseThrow(() -> new RuntimeException("Specie not found"));

        return new AnimalsReponse(
                animals.getId(),
                animals.getName(),
                animals.getSpecies(),
                animals.getBreed(),
                animals.getGender(),
                animals.getSize(),
                animals.getYears(),
                animals.getCastrated(),
                animals.getPhotoUrl(),
                animals.getHistory(),
                animals.getStatus(),
                animals.getClientId(),
                animals.getClientCpf(),
                animals.getClientName()
        );
    }

    public AnimalsReponse getAnimalsGender(Gender animalsGender){
        Animals animals = animalsRepository.findByGender(animalsGender)
                .orElseThrow(() -> new RuntimeException("Animal Gender not found"));

        return new AnimalsReponse(
                animals.getId(),
                animals.getName(),
                animals.getSpecies(),
                animals.getBreed(),
                animals.getGender(),
                animals.getSize(),
                animals.getYears(),
                animals.getCastrated(),
                animals.getPhotoUrl(),
                animals.getHistory(),
                animals.getStatus(),
                animals.getClientId(),
                animals.getClientCpf(),
                animals.getClientName()
        );
    }


    public AnimalsReponse updateAnimalStatus(Long id, AnimalStatus newStatus) throws NoSuchElementException {
        Optional<Animals> optionalAnimal = animalsRepository.findById(id);

        if (optionalAnimal.isEmpty()) {
            throw new NoSuchElementException("Animal not found");
        }

        Animals animal = optionalAnimal.get();
        animal.setStatus(newStatus);

        Animals savedAnimal = animalsRepository.save(animal);

        return new AnimalsReponse(
                savedAnimal.getId(),
                savedAnimal.getName(),
                savedAnimal.getSpecies(),
                savedAnimal.getBreed(),
                savedAnimal.getGender(),
                savedAnimal.getSize(),
                savedAnimal.getYears(),
                savedAnimal.getCastrated(),
                savedAnimal.getPhotoUrl(),
                savedAnimal.getHistory(),
                savedAnimal.getStatus(),
                savedAnimal.getClientId(),
                savedAnimal.getClientCpf(),
                savedAnimal.getClientName()
        );
    }

    public AnimalsReponse getAnimalsSize(AnimalSize size){
        Animals animals = animalsRepository.findBySize(size)
                .orElseThrow(() -> new RuntimeException("Animal Size not found"));

        return new AnimalsReponse(
                animals.getId(),
                animals.getName(),
                animals.getSpecies(),
                animals.getBreed(),
                animals.getGender(),
                animals.getSize(),
                animals.getYears(),
                animals.getCastrated(),
                animals.getPhotoUrl(),
                animals.getHistory(),
                animals.getStatus(),
                animals.getClientId(),
                animals.getClientCpf(),
                animals.getClientName()

        );
    }

    public AnimalsReponse getAnimalsStatus(AnimalStatus status){
        Animals animals = animalsRepository.findByStatus(status)
                .orElseThrow(() -> new RuntimeException("Animal Status not found"));

        return new AnimalsReponse(
                animals.getId(),
                animals.getName(),
                animals.getSpecies(),
                animals.getBreed(),
                animals.getGender(),
                animals.getSize(),
                animals.getYears(),
                animals.getCastrated(),
                animals.getPhotoUrl(),
                animals.getHistory(),
                animals.getStatus(),
                animals.getClientId(),
                animals.getClientCpf(),
                animals.getClientName()
        );
    }

    public List<AnimalsReponse> getAllAnimals(){
        List<Animals> animals = animalsRepository.findAll();
        return animals.stream()
                .map(allAnimals -> new AnimalsReponse(
                        allAnimals.getId(),
                        allAnimals.getName(),
                        allAnimals.getSpecies(),
                        allAnimals.getBreed(),
                        allAnimals.getGender(),
                        allAnimals.getSize(),
                        allAnimals.getYears(),
                        allAnimals.getCastrated(),
                        allAnimals.getPhotoUrl(),
                        allAnimals.getHistory(),
                        allAnimals.getStatus(),
                        allAnimals.getClientId(),
                        allAnimals.getClientCpf(),
                        allAnimals.getClientName()
                ))
                .collect(Collectors.toList());
    }
}

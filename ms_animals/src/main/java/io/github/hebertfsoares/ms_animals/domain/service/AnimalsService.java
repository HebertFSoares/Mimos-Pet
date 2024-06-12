package io.github.hebertfsoares.ms_animals.domain.service;

import io.github.hebertfsoares.ms_animals.domain.entities.Animals;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalStatus;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;
import io.github.hebertfsoares.ms_animals.domain.repository.AnimalsRepository;
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

    public AnimalsReponse saveAnimals(AnimalsRequest animalsRequest){
        Animals animals = new Animals(
                animalsRequest.name(),
                animalsRequest.species(),
                animalsRequest.breed(),
                animalsRequest.gender(),
                animalsRequest.size(),
                animalsRequest.years(),
                animalsRequest.castrated(),
                animalsRequest.photoUrl(),
                animalsRequest.history()
        );

        Animals savedAnimals = animalsRepository.save(animals);
        return new AnimalsReponse(
                savedAnimals.getId(),
                savedAnimals.getName(),
                savedAnimals.getSpecies(),
                savedAnimals.getBreed(),
                savedAnimals.getGender(),
                savedAnimals.getSize(),
                savedAnimals.getYears(),
                savedAnimals.getCastrated(),
                savedAnimals.getPhotoUrl(),
                savedAnimals.getHistory(),
                savedAnimals.getStatus()
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
                animals.getStatus()
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
                animals.getStatus()
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
                animals.getStatus()
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
                savedAnimal.getStatus()
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
                animals.getStatus()
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
                animals.getStatus()
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
                        allAnimals.getStatus()
                ))
                .collect(Collectors.toList());
    }
}

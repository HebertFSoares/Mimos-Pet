package io.github.hebertfsoares.ms_animals.web;

import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSize;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalSpecies;
import io.github.hebertfsoares.ms_animals.domain.enums.AnimalStatus;
import io.github.hebertfsoares.ms_animals.domain.enums.Gender;
import io.github.hebertfsoares.ms_animals.domain.service.AnimalsService;
import io.github.hebertfsoares.ms_animals.dto.AnimalsReponse;
import io.github.hebertfsoares.ms_animals.dto.AnimalsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("animals")
@AllArgsConstructor
@Tag(name = "Animal Controller", description = "Endpoints for managing animals")
public class AnimalsController {

    private final AnimalsService animalsService;

    @PostMapping("/register")
    @Operation(summary = "Register Animal", description = "Registers a new animal")
    public ResponseEntity<AnimalsReponse> registerAnimal(@RequestBody AnimalsRequest animalsRequest) {
        AnimalsReponse animalResponse = animalsService.saveAnimal(animalsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Id Animal", description = "Retrieves a animal for id")
    public ResponseEntity<AnimalsReponse> getAnimalsId(@PathVariable Long id){
        AnimalsReponse animalsReponse = animalsService.getAnimalsId(id);
        return ResponseEntity.ok(animalsReponse);
    }

    @GetMapping("/specie/{specie}")
    @Operation(summary = "Get Specie Animal", description = "Retrieves a animal for specie")
    public ResponseEntity<AnimalsReponse> getAnimalsSpecie(@PathVariable("specie")AnimalSpecies animalSpecies){
        AnimalsReponse animalsReponse = animalsService.getAnimalsSpecies(animalSpecies);
        return ResponseEntity.ok(animalsReponse);
    }

    @GetMapping("/gender/{gender}")
    @Operation(summary = "Get Gender Animal", description = "Retrieves a animal for gender")
    public ResponseEntity<AnimalsReponse> getAnimalGender(@PathVariable("gender") Gender animalGender){
        AnimalsReponse animalsReponse = animalsService.getAnimalsGender(animalGender);
        return ResponseEntity.ok(animalsReponse);
    }

    @GetMapping("/size/{size}")
    @Operation(summary = "Get Size Animal", description = "Retrieves a animal for size")
    public ResponseEntity<AnimalsReponse> getAnimalsSize(@PathVariable("size") AnimalSize animalSize){
        AnimalsReponse animalsReponse = animalsService.getAnimalsSize(animalSize);
        return ResponseEntity.ok(animalsReponse);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get Status Animal", description = "Retrieves a animal for status")
    public ResponseEntity<AnimalsReponse> getAnimalsStatus(@PathVariable("status") AnimalStatus animalStatus){
        AnimalsReponse animalsReponse = animalsService.getAnimalsStatus(animalStatus);
        return ResponseEntity.ok(animalsReponse);
    }

    @GetMapping
    @Operation(summary = "Get All Animal", description = "Retrieves a list of all animals")
    public ResponseEntity<List<AnimalsReponse>> getAllAnimals(){
        List<AnimalsReponse> responseList = animalsService.getAllAnimals();
        return ResponseEntity.ok(responseList);
    }


    @PutMapping("/status/{id}/{newStatus}")
    @Operation(summary = "Set New Status", description = "Set new status for the animal")
    public AnimalsReponse updateAnimalStatus(@PathVariable Long id, @PathVariable AnimalStatus newStatus) {
        return animalsService.updateAnimalStatus(id, newStatus);
    }

}

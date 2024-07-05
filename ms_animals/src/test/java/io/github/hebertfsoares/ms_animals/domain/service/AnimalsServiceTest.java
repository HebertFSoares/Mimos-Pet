package io.github.hebertfsoares.ms_animals.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.hebertfsoares.ms_animals.config.mq.AnimalPublisher;
import io.github.hebertfsoares.ms_animals.domain.entities.Animals;
import io.github.hebertfsoares.ms_animals.domain.entities.Client;
import io.github.hebertfsoares.ms_animals.domain.enums.*;
import io.github.hebertfsoares.ms_animals.domain.repository.AnimalsRepository;
import io.github.hebertfsoares.ms_animals.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_animals.dto.AnimalForAdoption;
import io.github.hebertfsoares.ms_animals.dto.AnimalsReponse;
import io.github.hebertfsoares.ms_animals.dto.AnimalsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalsServiceTest {

    @Mock
    private AnimalsRepository animalsRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AnimalPublisher animalPublisher;

    @InjectMocks
    private AnimalsService animalsService;

    private AnimalsRequest animalsRequest;
    private Client client;
    private Animals savedAnimal;

    @BeforeEach
    public void setUp() {
        client = new Client(1L, "TesteClient");
        animalsRequest = new AnimalsRequest(
                "PetTestes",
                AnimalSpecies.DOG,
                "Labrador",
                Gender.MALE,
                AnimalSize.SMALL,
                3,
                true,
                "photo",
                "history",
                1L
        );
        savedAnimal = new Animals(
                "PetTestes",
                AnimalSpecies.DOG,
                "Labrador",
                Gender.MALE,
                AnimalSize.SMALL,
                3,
                true,
                "photo",
                "history"
        );
        savedAnimal.setId(1L);
    }

    @Test
    @DisplayName("Deve retornar uma lista com apenas um animal")
    public void deveRetornarUmaListaComUmAnimal() {
        Animals animals = new Animals("PetTestes", AnimalSpecies.DOG, "Labrador",
                Gender.MALE, AnimalSize.SMALL, 3, true, "photo",
                "history");

        when(animalsRepository.findAll()).thenReturn(Collections.singletonList(animals));
        List<AnimalsReponse> animalsReponses = animalsService.getAllAnimals();

        assertEquals(1, animalsReponses.size());
    }

    @Test
    @DisplayName("Deve registrar um animal")
    public void deveRegistrarUmAnimal() throws JsonProcessingException {
        when(clientRepository.findById(animalsRequest.clientId())).thenReturn(Optional.of(client));
        when(animalsRepository.save(any(Animals.class))).thenAnswer(invocation -> {
            Animals animal = invocation.getArgument(0);
            animal.setId(1L);
            return animal;
        });
        doNothing().when(animalPublisher).sendAnimalInfo(any(AnimalForAdoption.class));

        AnimalsReponse animalsReponse = animalsService.saveAnimal(animalsRequest);

        assertNotNull(animalsReponse);
        assertEquals(1L, animalsReponse.id()); // Check against the expected ID set in the mock
        assertEquals(animalsRequest.name(), animalsReponse.name());
        assertEquals(animalsRequest.species(), animalsReponse.species());
        assertEquals(animalsRequest.breed(), animalsReponse.breed());
        assertEquals(animalsRequest.gender(), animalsReponse.gender());
        assertEquals(animalsRequest.size(), animalsReponse.size());
        assertEquals(animalsRequest.years(), animalsReponse.years());
        assertEquals(animalsRequest.castrated(), animalsReponse.Castrated());
        assertEquals(animalsRequest.photoUrl(), animalsReponse.photoUrl());
        assertEquals(animalsRequest.history(), animalsReponse.history());

        verify(clientRepository, times(1)).findById(animalsRequest.clientId());
        verify(animalsRepository, times(1)).save(any(Animals.class));
        verify(animalPublisher, times(1)).sendAnimalInfo(any(AnimalForAdoption.class));
    }

    @Test
    @DisplayName("Deve retornar um animal por ID")
    public void deveRetornarAnimalPorId(){
        Long id = 1L;
        Animals animals = new Animals(id,"PetTestes", AnimalSpecies.DOG, "Labrador",
                Gender.MALE, AnimalSize.SMALL, 3, true, "photo",
                "history",AnimalStatus.AVAILABLE, 1L, "ClientTest",
                "123456789-10");
        when(animalsRepository.findById(id)).thenReturn(Optional.of(animals));

        AnimalsReponse reponse = animalsService.getAnimalsId(id);

        assertEquals(animals.getId(), reponse.id());
        assertEquals(animals.getName(), reponse.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o animal não for encontrado por ID")
    public void deveLancarExcecaoQuandoAnimalNaoForEncontradoPorId(){
        Long id = 1L;

        when(animalsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> animalsService.getAnimalsId(id));
    }

    @Test
    @DisplayName("Deve retornar um animals por Specie")
    public void deveRetornarAnimalPorSpecie(){
        AnimalSpecies species = AnimalSpecies.DOG;
        Animals animals = new Animals(1L, "PetTestes", species, "Labrador",
                Gender.MALE, AnimalSize.SMALL, 3, true, "photo",
                "history",AnimalStatus.AVAILABLE, 1L, "ClientTest",
                "123456789-10");
        when(animalsRepository.findBySpecies(species)).thenReturn(Optional.of(animals));

        AnimalsReponse reponse = animalsService.getAnimalsSpecies(species);

        assertEquals(animals.getSpecies(), reponse.species());
        assertEquals(animals.getName(), reponse.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o animal não for encontrado por Species")
    public void deveLancarExcecaoQuandoAnimalNaoForEncontradoPorSpecie(){
        AnimalSpecies species = AnimalSpecies.CAT;

        when(animalsRepository.findBySpecies(species)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> animalsService.getAnimalsSpecies(species));
    }

    @Test
    @DisplayName("Deve retornar um animals por Gender")
    public void deveRetonarAnimalPorGender(){
        Gender gender = Gender.FEMALE;
        Animals animals = new Animals(1L, "PetTestes", AnimalSpecies.CAT, "Labrador",
                gender, AnimalSize.SMALL, 3, true, "photo",
                "history",AnimalStatus.AVAILABLE, 1L, "ClientTest",
                "123456789-10");
        when(animalsRepository.findByGender(gender)).thenReturn(Optional.of(animals));

        AnimalsReponse reponse = animalsService.getAnimalsGender(gender);

        assertEquals(animals.getGender(), reponse.gender());
        assertEquals(animals.getName(), reponse.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o animal não for encontrado por Gender")
    public void deveLancarExcecaoQuandoAnimalNaoForEncontradoPorGender(){
        Gender gender = Gender.FEMALE;

        when(animalsRepository.findByGender(gender)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> animalsService.getAnimalsGender(gender));
    }

    @Test
    @DisplayName("Deve retornar um animal por Size")
    public void deveRetornarAnimalPorSize(){
        AnimalSize size = AnimalSize.LARGE;
        Animals animals = new Animals(1L, "PetTestes", AnimalSpecies.CAT, "Labrador",
                Gender.MALE, size, 3, true, "photo",
                "history",AnimalStatus.AVAILABLE, 1L, "ClientTest",
                "123456789-10");
        when(animalsRepository.findBySize(size)).thenReturn(Optional.of(animals));

        AnimalsReponse reponse = animalsService.getAnimalsSize(size);

        assertEquals(animals.getSize(), reponse.size());
        assertEquals(animals.getName(), reponse.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o animal não for encontrado por Size")
    public void deveLancarExcecaoQuandoAnimalNaoForEncontradoPorSize(){
        AnimalSize size = AnimalSize.LARGE;

        when(animalsRepository.findBySize(size)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> animalsService.getAnimalsSize(size));
    }

    @Test
    @DisplayName("Deve retornar um animal pelo Status")
    public void getAnimalsStatusTest() {
        AnimalStatus status = AnimalStatus.AVAILABLE;
        Animals animals = new Animals(1L, "PetTestes", AnimalSpecies.CAT, "Labrador",
                Gender.MALE, AnimalSize.SMALL, 3, true, "photo",
                "history", status, 1L, "ClientTest",
                "123456789-10");
        when(animalsRepository.findByStatus(status)).thenReturn(Optional.of(animals));

        AnimalsReponse reponse = animalsService.getAnimalsStatus(status);

        assertEquals(animals.getStatus(), reponse.status());
        assertEquals(animals.getName(), reponse.name());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando um animal não for achado com o Status de ADOPTED")
    void getAnimalsStatusNotFoundTest() {
        when(animalsRepository.findByStatus(AnimalStatus.ADOPTED)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> animalsService.getAnimalsStatus(AnimalStatus.ADOPTED));
        assertEquals("Animal Status not found", exception.getMessage());

        verify(animalsRepository, times(1)).findByStatus(AnimalStatus.ADOPTED);
    }

    @Test
    @DisplayName("Deve retornar um animal atualizado")
    public void updateAnimalStatusTest() {
        when(animalsRepository.findById(1L)).thenReturn(Optional.of(savedAnimal));
        when(animalsRepository.save(any(Animals.class))).thenReturn(savedAnimal);

        AnimalsReponse response = animalsService.updateAnimalStatus(1L, AnimalStatus.ADOPTED);

        assertNotNull(response);
        assertEquals(savedAnimal.getId(), response.id());
        assertEquals(savedAnimal.getName(), response.name());
        assertEquals(savedAnimal.getSpecies(), response.species());
        assertEquals(savedAnimal.getBreed(), response.breed());
        assertEquals(savedAnimal.getGender(), response.gender());
        assertEquals(savedAnimal.getSize(), response.size());
        assertEquals(savedAnimal.getYears(), response.years());
        assertEquals(savedAnimal.getCastrated(), response.Castrated());
        assertEquals(savedAnimal.getPhotoUrl(), response.photoUrl());
        assertEquals(savedAnimal.getHistory(), response.history());
        assertEquals(AnimalStatus.ADOPTED, response.status());

        verify(animalsRepository, times(1)).findById(1L);
        verify(animalsRepository, times(1)).save(any(Animals.class));
    }



    @Test
    @DisplayName("Deve lançar uma exceção quando o animal não for encontrado para ser atualizado")
    public void updateAnimalStatusNotFoundTest() {
        when(animalsRepository.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> animalsService.updateAnimalStatus(1L, AnimalStatus.ADOPTED));
        assertEquals("Animal not found", exception.getMessage());

        verify(animalsRepository, times(1)).findById(1L);
        verify(animalsRepository, times(0)).save(any(Animals.class));
    }

    @Test
    @DisplayName("Deve registrar um animal e lançar JsonProcessingException")
    public void deveLancarJsonProcessingException() throws JsonProcessingException {
        when(clientRepository.findById(animalsRequest.clientId())).thenReturn(Optional.of(client));
        when(animalsRepository.save(any(Animals.class))).thenAnswer(invocation -> {
            Animals animal = invocation.getArgument(0);
            animal.setId(1L);
            return animal;
        });
        doThrow(JsonProcessingException.class).when(animalPublisher).sendAnimalInfo(any(AnimalForAdoption.class));

        AnimalsReponse animalsReponse = animalsService.saveAnimal(animalsRequest);

        assertNotNull(animalsReponse);
        assertEquals(1L, animalsReponse.id());
        assertEquals(animalsRequest.name(), animalsReponse.name());
        assertEquals(animalsRequest.species(), animalsReponse.species());
        assertEquals(animalsRequest.breed(), animalsReponse.breed());
        assertEquals(animalsRequest.gender(), animalsReponse.gender());
        assertEquals(animalsRequest.size(), animalsReponse.size());
        assertEquals(animalsRequest.years(), animalsReponse.years());
        assertEquals(animalsRequest.castrated(), animalsReponse.Castrated());
        assertEquals(animalsRequest.photoUrl(), animalsReponse.photoUrl());
        assertEquals(animalsRequest.history(), animalsReponse.history());

        verify(clientRepository, times(1)).findById(animalsRequest.clientId());
        verify(animalsRepository, times(1)).save(any(Animals.class));
        verify(animalPublisher, times(1)).sendAnimalInfo(any(AnimalForAdoption.class));
    }
}

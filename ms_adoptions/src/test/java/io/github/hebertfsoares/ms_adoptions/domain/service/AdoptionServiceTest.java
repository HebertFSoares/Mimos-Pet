package io.github.hebertfsoares.ms_adoptions.domain.service;

import io.github.hebertfsoares.ms_adoptions.domain.entities.Adoption;
import io.github.hebertfsoares.ms_adoptions.domain.entities.Animal;
import io.github.hebertfsoares.ms_adoptions.domain.entities.Client;
import io.github.hebertfsoares.ms_adoptions.domain.repository.AdoptionRepository;
import io.github.hebertfsoares.ms_adoptions.domain.repository.AnimalRepository;
import io.github.hebertfsoares.ms_adoptions.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_adoptions.dto.AdoptionResponse;
import io.github.hebertfsoares.ms_adoptions.dto.AdptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdoptionServiceTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AdoptionService adoptionService;

    private AdptionRequest adoptionRequest;
    private Animal animal;
    private Client client;
    private Adoption savedAdoption;

    @BeforeEach
    void setUp() {
        adoptionRequest = new AdptionRequest(1L, 1L, "Test observations");
        animal = new Animal(1L, "Test Animal");
        client = new Client(1L, "Test Client");
        savedAdoption = new Adoption();
        savedAdoption.setId(1L);
        savedAdoption.setAnimalId(1L);
        savedAdoption.setClientId(1L);
        savedAdoption.setObservations("Test observations");
        savedAdoption.setAnimalName("Test Animal");
        savedAdoption.setClientName("Test Client");
    }

    @Test
    @DisplayName("Deve registrar uma nova Adoção")
    void deveRegistrarNovaAdocao() {
        when(animalRepository.findById(adoptionRequest.getAnimalId())).thenReturn(Optional.of(animal));
        when(clientRepository.findById(adoptionRequest.getClientId())).thenReturn(Optional.of(client));
        when(adoptionRepository.save(any(Adoption.class))).thenAnswer(invocation -> {
            Adoption adoption = invocation.getArgument(0);
            adoption.setId(1L);
            return adoption;
        });

        AdoptionResponse response = adoptionService.adopt(adoptionRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(adoptionRequest.getAnimalId(), response.getAnimalId());
        assertEquals(adoptionRequest.getClientId(), response.getClientId());
        assertEquals("Test observations", response.getObservations());
        assertEquals("Test Animal", response.getAnimalName());
        assertEquals("Test Client", response.getClientName());

        verify(animalRepository, times(1)).findById(adoptionRequest.getAnimalId());
        verify(clientRepository, times(1)).findById(adoptionRequest.getClientId());
        verify(adoptionRepository, times(1)).save(any(Adoption.class));
    }

    @Test
    @DisplayName("Deve lançar uma exceção se o animal não existir")
    void deveLancarExcecaoSeAnimalNaoExistir() {
        when(animalRepository.findById(adoptionRequest.getAnimalId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adoptionService.adopt(adoptionRequest));
        assertEquals("Animal not found", exception.getMessage());

        verify(animalRepository, times(1)).findById(adoptionRequest.getAnimalId());
        verify(clientRepository, times(0)).findById(adoptionRequest.getClientId());
        verify(adoptionRepository, times(0)).save(any(Adoption.class));
    }

    @Test
    @DisplayName("Deve lançar uma exceção se o cliente não existir")
    void deveLancarExcecaoSeClienteNaoExistir() {
        when(animalRepository.findById(adoptionRequest.getAnimalId())).thenReturn(Optional.of(animal));
        when(clientRepository.findById(adoptionRequest.getClientId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adoptionService.adopt(adoptionRequest));
        assertEquals("Client not found", exception.getMessage());

        verify(animalRepository, times(1)).findById(adoptionRequest.getAnimalId());
        verify(clientRepository, times(1)).findById(adoptionRequest.getClientId());
        verify(adoptionRepository, times(0)).save(any(Adoption.class));
    }
}

package io.github.hebertfsoares.ms_client.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.hebertfsoares.ms_client.config.mq.ClientPublisher;
import io.github.hebertfsoares.ms_client.domain.entities.Client;
import io.github.hebertfsoares.ms_client.domain.enums.roleClient;
import io.github.hebertfsoares.ms_client.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_client.dto.ClientForAnimals;
import io.github.hebertfsoares.ms_client.dto.ClientRequest;
import io.github.hebertfsoares.ms_client.dto.ClientResponse;
import io.github.hebertfsoares.ms_client.dto.ClientUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ClientPublisher clientPublisher;

    @Test
    @DisplayName("Deve retornar uma lista com apenas um cliente")
    public void deveRetornarUmaListaComUmClient(){
        Client client = new Client(1L, "lucas", "lucasteste@teste.com","teste123"
                ,"123.456.789-10", "Rua teste", 1234567890, roleClient.CLIENT);
        when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        List<ClientResponse> clients = clientService.getAllClients();

        Assertions.assertEquals(1,clients.size());
    }

    @Test
    @DisplayName("Deve registrar um client")
    public void deveRegistrarUmClient() throws JsonProcessingException {
        ClientRequest clientRequest = new ClientRequest("lucas", "lucasteste@teste.com",
                "teste123", "123.456.789-10", "Rua teste", 1234567890);
        Client client = new Client(1L, "lucas", "lucasteste@teste.com",
                "hashedPassword", "123.456.789-10", "Rua teste", 1234567890,
                roleClient.CLIENT);

        when(passwordEncoder.encode(clientRequest.password())).thenReturn("hashedPassword");
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        doNothing().when(clientPublisher).sendClientInfo(any(ClientForAnimals.class));

        ClientResponse clientResponse = clientService.registerClient(clientRequest);

        assertNotNull(clientResponse);
        assertEquals(client.getId(), clientResponse.id());
        assertEquals(client.getName(), clientResponse.name());
        assertEquals(client.getEmail(), clientResponse.email());
        assertEquals(client.getCpf(), clientResponse.cpf());
        assertEquals(client.getAddress(), clientResponse.address());
        assertEquals(client.getPhone(), clientResponse.phone());
        assertEquals(client.getRole(), clientResponse.role());

        verify(clientRepository, times(1)).save(any(Client.class));
        verify(clientPublisher, times(1)).sendClientInfo(any(ClientForAnimals.class));
    }

    @Test
    @DisplayName("Deve retornar um cliente por cpf")
    public void deveRetornarUmClientePorCpf(){
        String cpf = "123.456.789-10";
        Client client = new Client(1l,"lucas", "lucasteste@teste.com", "teste123",
                cpf, "Rua teste", 1234567890, roleClient.CLIENT);

        when(clientRepository.findByCpf(cpf)).thenReturn(Optional.of(client));

        ClientResponse response = clientService.getClientByCpf(cpf);

        assertEquals(client.getId(), response.id());
        assertEquals(client.getName(), response.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cliente não for encontrado por CPF")
    public void deveLancarExcecaoQuandoClienteNaoEncontradoPorCpf() {
        String cpf = "123.456.789-10";

        when(clientRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.getClientByCpf(cpf));
    }

    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    public void deveAtualizarClienteComSucesso() {
        Long id = 1L;
        ClientUpdateRequest request = new ClientUpdateRequest("lucas atualizado", "lucas.atualizado@teste.com", "teste1234", "123.456.789-11", "Rua nova", 1234567891, roleClient.CLIENT);
        Client client = new Client(id, "lucas", "lucasteste@teste.com", "teste123", "123.456.789-10", "Rua teste", 1234567890, roleClient.CLIENT);
        Client updatedClient = new Client(id, "lucas atualizado", "lucas.atualizado@teste.com", "hashedPassword", "123.456.789-11", "Rua nova", 1234567891, roleClient.CLIENT);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(passwordEncoder.encode(request.password())).thenReturn("hashedPassword");
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        ClientResponse response = clientService.updateClient(id, request);

        assertEquals(updatedClient.getId(), response.id());
        assertEquals(updatedClient.getName(), response.name());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um cliente inexistente")
    public void deveLancarExcecaoAoAtualizarClienteInexistente() {
        Long id = 1L;
        ClientUpdateRequest request = new ClientUpdateRequest("lucas atualizado", "lucas.atualizado@teste.com", "teste1234", "123.456.789-11", "Rua nova", 1234567891, roleClient.CLIENT);

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.updateClient(id, request));
    }

    @Test
    @DisplayName("Deve deletar um cliente com sucesso")
    public void deveDeletarClienteComSucesso() {
        Long id = 1L;

        when(clientRepository.existsById(id)).thenReturn(true);

        clientService.deleteClient(id);

        verify(clientRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um cliente inexistente")
    public void deveLancarExcecaoAoDeletarClienteInexistente() {
        Long id = 1L;

        when(clientRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> clientService.deleteClient(id));
    }
}

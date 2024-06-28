package io.github.hebertfsiares.ms_client.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.hebertfsiares.ms_client.config.mq.ClientPublisher;
import io.github.hebertfsiares.ms_client.domain.entities.Client;
import io.github.hebertfsiares.ms_client.domain.enums.roleClient;
import io.github.hebertfsiares.ms_client.domain.repository.ClientRepository;
import io.github.hebertfsiares.ms_client.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientPublisher clientPublisher;

    public ClientResponse registerClient(ClientRequest clientRequest) {
        Client client = new Client(
                clientRequest.name(),
                clientRequest.email(),
                passwordEncoder.encode(clientRequest.password()),
                clientRequest.cpf(),
                clientRequest.address(),
                clientRequest.phone(),
                roleClient.ADMIN
        );
        Client savedClient = clientRepository.save(client);

        try {
            ClientForAnimals clientForAnimals = new ClientForAnimals(savedClient.getId(), savedClient.getName());
            clientPublisher.sendClientInfo(clientForAnimals);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ClientResponse(
                savedClient.getId(),
                savedClient.getName(),
                savedClient.getEmail(),
                savedClient.getCpf(),
                savedClient.getAddress(),
                savedClient.getPhone(),
                savedClient.getRole()
        );
    }

    public ClientResponse getClientByCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCpf(),
                client.getAddress(),
                client.getPhone(),
                client.getRole()
        );
    }

    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getEmail(),
                        client.getCpf(),
                        client.getAddress(),
                        client.getPhone(),
                        client.getRole()
                ))
                .collect(Collectors.toList());
    }

    public ClientResponse updateClient(Long id, ClientUpdateRequest clientUpdateRequest) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setName(clientUpdateRequest.name());
        client.setEmail(clientUpdateRequest.email());
        if (clientUpdateRequest.password() != null) {
            client.setPassword(passwordEncoder.encode(clientUpdateRequest.password()));
        }
        client.setCpf(clientUpdateRequest.cpf());
        client.setAddress(clientUpdateRequest.address());
        client.setPhone(clientUpdateRequest.phone());
        client.setRole(clientUpdateRequest.role());

        Client updatedClient = clientRepository.save(client);

        return new ClientResponse(
                updatedClient.getId(),
                updatedClient.getName(),
                updatedClient.getEmail(),
                updatedClient.getCpf(),
                updatedClient.getAddress(),
                updatedClient.getPhone(),
                updatedClient.getRole()
        );
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }
}

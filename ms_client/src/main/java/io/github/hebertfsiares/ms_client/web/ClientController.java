package io.github.hebertfsiares.ms_client.web;

import io.github.hebertfsiares.ms_client.domain.services.ClientService;
import io.github.hebertfsiares.ms_client.dto.ClientRequest;
import io.github.hebertfsiares.ms_client.dto.ClientResponse;
import io.github.hebertfsiares.ms_client.dto.ClientUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponse> registerClient(@RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientService.registerClient(clientRequest);
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientResponse> getClientByCpf(@PathVariable String cpf) {
        ClientResponse clientResponse = clientService.getClientByCpf(cpf);
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody ClientUpdateRequest clientUpdateRequest) {
        ClientResponse clientResponse = clientService.updateClient(id, clientUpdateRequest);
        return ResponseEntity.ok(clientResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

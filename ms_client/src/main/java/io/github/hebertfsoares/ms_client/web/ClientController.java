package io.github.hebertfsoares.ms_client.web;

import io.github.hebertfsoares.ms_client.domain.services.ClientService;
import io.github.hebertfsoares.ms_client.dto.ClientRequest;
import io.github.hebertfsoares.ms_client.dto.ClientResponse;
import io.github.hebertfsoares.ms_client.dto.ClientUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
@Tag(name = "Client Controller", description = "Endpoints for managing clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register Client", description = "Registers a new client")
    public ResponseEntity<ClientResponse> registerClient(@RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientService.registerClient(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse);
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Get Client by CPF", description = "Retrieves a client's details by CPF")
    public ResponseEntity<ClientResponse> getClientByCpf(@PathVariable String cpf) {
        ClientResponse clientResponse = clientService.getClientByCpf(cpf);
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get All Clients", description = "Retrieves a list of all clients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Client", description = "Updates the details of an existing client by ID")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody ClientUpdateRequest clientUpdateRequest) {
        ClientResponse clientResponse = clientService.updateClient(id, clientUpdateRequest);
        return ResponseEntity.ok(clientResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Client", description = "Deletes an existing client by ID")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

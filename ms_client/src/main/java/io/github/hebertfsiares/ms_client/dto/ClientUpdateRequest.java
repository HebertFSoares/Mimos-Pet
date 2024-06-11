package io.github.hebertfsiares.ms_client.dto;

public record ClientUpdateRequest(String name, String email, String password, String cpf, String address, Integer phone) {
}

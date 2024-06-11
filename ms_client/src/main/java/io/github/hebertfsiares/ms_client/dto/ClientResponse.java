package io.github.hebertfsiares.ms_client.dto;

import io.github.hebertfsiares.ms_client.domain.enums.roleClient;

public record ClientResponse(Long id, String name, String email, String cpf, String address, Integer phone, roleClient role) {
}

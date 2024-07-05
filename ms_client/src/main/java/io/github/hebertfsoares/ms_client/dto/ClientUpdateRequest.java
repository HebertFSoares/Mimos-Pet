package io.github.hebertfsoares.ms_client.dto;

import io.github.hebertfsoares.ms_client.domain.enums.roleClient;

public record ClientUpdateRequest(String name, String email, String password, String cpf, String address, Integer phone, roleClient role) {
}

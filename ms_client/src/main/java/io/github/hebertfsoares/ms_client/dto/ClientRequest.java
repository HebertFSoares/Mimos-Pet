package io.github.hebertfsoares.ms_client.dto;

public record ClientRequest(String name, String email, String password, String cpf, String address, Integer phone) {
}

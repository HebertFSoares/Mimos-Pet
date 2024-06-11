package io.github.hebertfsiares.ms_client.dto;

public record LoginResponse(String accessToken, Long expireIn) {
}

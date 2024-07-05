package io.github.hebertfsoares.ms_client.dto;

public record LoginResponse(String accessToken, Long expireIn) {
}

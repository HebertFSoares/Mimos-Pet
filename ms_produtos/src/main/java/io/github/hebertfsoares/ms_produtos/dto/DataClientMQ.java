package io.github.hebertfsoares.ms_produtos.dto;

import lombok.Data;

@Data
public class DataClientMQ {
    private Long id;
    private String cpf;
    private String name;
    private String address;
    private String email;
}

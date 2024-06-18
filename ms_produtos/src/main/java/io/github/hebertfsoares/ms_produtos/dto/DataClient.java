package io.github.hebertfsoares.ms_produtos.dto;

import io.github.hebertfsoares.ms_produtos.domain.enums.roleClient;
import lombok.Data;

@Data
public class DataClient {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String address;
    private Integer phone;
    private roleClient role = roleClient.CLIENT;
}

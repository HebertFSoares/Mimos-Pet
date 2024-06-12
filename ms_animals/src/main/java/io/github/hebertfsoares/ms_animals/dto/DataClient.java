package io.github.hebertfsoares.ms_animals.dto;

import io.github.hebertfsoares.ms_animals.domain.enums.roleClient;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class DataClient {
    private Long id;
    @Column
    private String name;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column(unique = true)
    private String cpf;
    @Column
    private String address;
    @Column
    private Integer phone;
    @Enumerated(EnumType.STRING)
    @Column
    private roleClient role = roleClient.CLIENT;

}

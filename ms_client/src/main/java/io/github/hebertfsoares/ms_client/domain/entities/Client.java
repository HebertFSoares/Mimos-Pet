package io.github.hebertfsoares.ms_client.domain.entities;


import io.github.hebertfsoares.ms_client.domain.enums.roleClient;
import io.github.hebertfsoares.ms_client.dto.LoginRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Client(String name, String email, String password, String cpf, String address, Integer phone, roleClient role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }
    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}

package io.github.hebertfsoares.ms_animals.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private Long id;
    private String name;
}
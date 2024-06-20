package io.github.hebertfsoares.ms_sales.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private Long id;
    private String name;
}

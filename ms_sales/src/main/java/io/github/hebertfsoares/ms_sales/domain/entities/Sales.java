package io.github.hebertfsoares.ms_sales.domain.entities;

import io.github.hebertfsoares.ms_sales.domain.enums.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long clientId;
    private LocalDateTime dateSale;
    private Integer quantity;
    private Integer amount;
    private Payment payment;

    @PrePersist
    protected void onCreate() {
        this.dateSale = LocalDateTime.now();
    }
}

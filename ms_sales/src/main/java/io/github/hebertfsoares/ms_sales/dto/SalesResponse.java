package io.github.hebertfsoares.ms_sales.dto;

import io.github.hebertfsoares.ms_sales.domain.enums.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Long clientId;
    private String clientName;
    private LocalDateTime dateSale;
    private Integer quantity;
    private Integer amount;
    private Payment payment;
}
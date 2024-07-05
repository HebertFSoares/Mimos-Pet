package io.github.hebertfsoares.ms_sales.dto;

import io.github.hebertfsoares.ms_sales.domain.enums.Payment;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesRequest {
    private Long productId;
    private Long clientId;
    private Integer quantity;
    private Integer amount;
    private Payment payment;
}
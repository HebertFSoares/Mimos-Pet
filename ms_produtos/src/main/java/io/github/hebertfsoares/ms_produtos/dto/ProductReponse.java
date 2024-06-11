package io.github.hebertfsoares.ms_produtos.dto;

import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;

import java.math.BigDecimal;

public record ProductReponse(Long id, String name, String description, ProductCategory category, String brand, BigDecimal price, Integer stock, String photoUrl) {
}

package io.github.hebertfsoares.ms_sales.domain.repository;

import io.github.hebertfsoares.ms_sales.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
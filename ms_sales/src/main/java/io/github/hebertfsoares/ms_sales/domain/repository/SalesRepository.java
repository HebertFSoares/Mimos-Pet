package io.github.hebertfsoares.ms_sales.domain.repository;

import io.github.hebertfsoares.ms_sales.domain.entities.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
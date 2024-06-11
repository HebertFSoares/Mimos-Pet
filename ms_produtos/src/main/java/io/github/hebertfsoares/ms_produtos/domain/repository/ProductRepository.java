package io.github.hebertfsoares.ms_produtos.domain.repository;

import io.github.hebertfsoares.ms_produtos.domain.entities.Product;
import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCategory(ProductCategory category);
}
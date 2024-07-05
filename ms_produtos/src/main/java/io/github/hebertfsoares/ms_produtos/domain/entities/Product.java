package io.github.hebertfsoares.ms_produtos.domain.entities;

import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    @Column
    private ProductCategory category;
    @Column
    private String brand;
    @Column
    private BigDecimal price;
    @Column
    private Integer stock;
    @Column
    private String photoUrl;


    public Product(String name, String description, ProductCategory category, String brand, BigDecimal price, Integer stock, String photoUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.photoUrl = photoUrl;
    }
}

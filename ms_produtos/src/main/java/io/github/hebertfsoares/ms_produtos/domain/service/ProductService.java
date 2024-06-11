package io.github.hebertfsoares.ms_produtos.domain.service;

import io.github.hebertfsoares.ms_produtos.domain.entities.Product;
import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import io.github.hebertfsoares.ms_produtos.domain.repository.ProductRepository;
import io.github.hebertfsoares.ms_produtos.dto.ProductReponse;
import io.github.hebertfsoares.ms_produtos.dto.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductReponse saveProduct(ProductRequest productRequest){
        Product product = new Product(
                productRequest.name(),
                productRequest.description(),
                productRequest.category(),
                productRequest.brand(),
                productRequest.price(),
                productRequest.stock(),
                productRequest.photoUrl()
        );

        Product savedProduct = productRepository.save(product);
        return new ProductReponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getCategory(),
                savedProduct.getBrand(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getPhotoUrl()
        );
    }

    public ProductReponse getProductId(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductReponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getStock(),
                product.getPhotoUrl()
        );
    }

    public ProductReponse getProductCategory(ProductCategory category){
        Product product = productRepository.findByCategory(category)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductReponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getStock(),
                product.getPhotoUrl()
        );
    }

    public List<ProductReponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductReponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getBrand(),
                        product.getPrice(),
                        product.getStock(),
                        product.getPhotoUrl()
                ))
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}

package io.github.hebertfsoares.ms_produtos.domain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.hebertfsoares.ms_produtos.config.mq.ProductPublisher;
import io.github.hebertfsoares.ms_produtos.domain.entities.Product;
import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import io.github.hebertfsoares.ms_produtos.domain.repository.ProductRepository;
import io.github.hebertfsoares.ms_produtos.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductPublisher productPublisher;

    @Test
    @DisplayName("Deve retornar uma lista com apenas um produto")
    public void deveRetornarUmaListaComUmProduto() {
        Product product = new Product("Product A", "Description A", ProductCategory.FOOD, "Brand A", new BigDecimal("100.0"), 10, "photoUrl");
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        List<ProductReponse> products = productService.getAllProducts();

        assertEquals(1, products.size());
    }

    @Test
    @DisplayName("Deve registrar um produto")
    public void deveRegistrarUmProduto() throws JsonProcessingException {
        ProductRequest productRequest = new ProductRequest("Product A", "Description A", ProductCategory.FOOD, "Brand A", new BigDecimal("100.0"), 10, "photoUrl");
        Product savedProduct = new Product(1L, "Product A", "Description A", ProductCategory.FOOD, "Brand A", new BigDecimal("100.0"), 10, "photoUrl");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        doNothing().when(productPublisher).sendProductInfo(any(ProductForSales.class));

        ProductReponse productResponse = productService.saveProduct(productRequest);

        assertNotNull(productResponse);
        assertEquals(savedProduct.getId(), productResponse.id());
        assertEquals(savedProduct.getName(), productResponse.name());
        assertEquals(savedProduct.getDescription(), productResponse.description());
        assertEquals(savedProduct.getCategory(), productResponse.category());
        assertEquals(savedProduct.getBrand(), productResponse.brand());
        assertEquals(savedProduct.getPrice(), productResponse.price());
        assertEquals(savedProduct.getStock(), productResponse.stock());
        assertEquals(savedProduct.getPhotoUrl(), productResponse.photoUrl());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productPublisher, times(1)).sendProductInfo(any(ProductForSales.class));
    }

    @Test
    @DisplayName("Deve retornar um produto por ID")
    public void deveRetornarProdutoPorId() {
        Long id = 1L;
        Product product = new Product(id, "Product A", "Description A", ProductCategory.FOOD, "Brand A", new BigDecimal("100.0"), 10, "photoUrl");

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductReponse response = productService.getProductId(id);

        assertEquals(product.getId(), response.id());
        assertEquals(product.getName(), response.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto não for encontrado por ID")
    public void deveLancarExcecaoQuandoProdutoNaoEncontradoPorId() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductId(id));
    }

    @Test
    @DisplayName("Deve retornar um produto por Category")
    public void deveRetornarProdutoPorCategory() {
        ProductCategory category = ProductCategory.FOOD;
        Product product = new Product(1L, "Product A", "Description A", category, "Brand A", new BigDecimal("100.0"), 10, "photoUrl");

        when(productRepository.findByCategory(category)).thenReturn(Optional.of(product));

        ProductReponse response = productService.getProductCategory(category);

        assertEquals(product.getCategory(), response.category());
        assertEquals(product.getName(), response.name());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto não for encontrado por Category")
    public void deveLancarExcecaoQuandoProdutoNaoEncontradoPorCategory() {
        ProductCategory category = ProductCategory.FOOD;

        when(productRepository.findByCategory(category)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductCategory(category));
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    public void deveDeletarProdutoComSucesso() {
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(true);

        productService.deleteProduct(id);

        verify(productRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um produto inexistente")
    public void deveLancarExcecaoAoDeletarProdutoInexistente() {
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> productService.deleteProduct(id));
    }
}
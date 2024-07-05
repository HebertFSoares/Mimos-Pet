package io.github.hebertfsoares.ms_produtos.web;

import io.github.hebertfsoares.ms_produtos.domain.entities.Clients;
import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import io.github.hebertfsoares.ms_produtos.domain.exception.DadosClientException;
import io.github.hebertfsoares.ms_produtos.domain.exception.ErroSolicitacaoClientException;
import io.github.hebertfsoares.ms_produtos.domain.exception.ErrorMicrosericeException;
import io.github.hebertfsoares.ms_produtos.domain.service.ProductService;
import io.github.hebertfsoares.ms_produtos.dto.DataClientMQ;
import io.github.hebertfsoares.ms_produtos.dto.ProductReponse;
import io.github.hebertfsoares.ms_produtos.dto.ProductRequest;
import io.github.hebertfsoares.ms_produtos.dto.ProtocoloGetClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@AllArgsConstructor
@Tag(name = "Product Controller", description = "Endpoints for Products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    @Operation(summary = "Register Product", description = "Registers a new product")
    public ResponseEntity<ProductReponse> registerProduct(@RequestBody ProductRequest productRequest){
        ProductReponse productReponse = productService.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productReponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Id Animal", description = "Retrieves a animal for id")
    public ResponseEntity<ProductReponse> getProductId(@PathVariable Long id){
        ProductReponse productReponse = productService.getProductId(id);
        return ResponseEntity.ok(productReponse);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get Category Product", description = "Retrieves a product for category")
    public ResponseEntity<ProductReponse> getProductCategory(@PathVariable("category") ProductCategory productCategory){
        ProductReponse productReponse = productService.getProductCategory(productCategory);
        return ResponseEntity.ok(productReponse);
    }

    @GetMapping
    @Operation(summary = "Get All Product", description = "Retrieves a list of all products")
    public ResponseEntity<List<ProductReponse>> getAllProduct(){
        List<ProductReponse> reponseList = productService.getAllProducts();
        return ResponseEntity.ok(reponseList);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted Product", description = "Delete product with id")
    public ResponseEntity<ProductReponse> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
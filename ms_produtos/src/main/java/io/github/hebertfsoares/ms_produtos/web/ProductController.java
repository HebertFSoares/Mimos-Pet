package io.github.hebertfsoares.ms_produtos.web;

import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import io.github.hebertfsoares.ms_produtos.domain.service.ProductService;
import io.github.hebertfsoares.ms_produtos.dto.ProductReponse;
import io.github.hebertfsoares.ms_produtos.dto.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<ProductReponse> registerProduct(@RequestBody ProductRequest productRequest){
        ProductReponse productReponse = productService.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productReponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReponse> getProductId(@PathVariable Long id){
        ProductReponse productReponse = productService.getProductId(id);
        return ResponseEntity.ok(productReponse);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ProductReponse> getProductCategory(@PathVariable("category") ProductCategory productCategory){
        ProductReponse productReponse = productService.getProductCategory(productCategory);
        return ResponseEntity.ok(productReponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductReponse>> getAllProduct(){
        List<ProductReponse> reponseList = productService.getAllProducts();
        return ResponseEntity.ok(reponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductReponse> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

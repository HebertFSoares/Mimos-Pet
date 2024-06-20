package io.github.hebertfsoares.ms_produtos.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import io.github.hebertfsoares.ms_produtos.config.mq.ProductPublisher;
import io.github.hebertfsoares.ms_produtos.domain.entities.Clients;
import io.github.hebertfsoares.ms_produtos.domain.entities.Product;
import io.github.hebertfsoares.ms_produtos.domain.enums.ProductCategory;
import io.github.hebertfsoares.ms_produtos.domain.exception.DadosClientException;
import io.github.hebertfsoares.ms_produtos.domain.exception.ErroSolicitacaoClientException;
import io.github.hebertfsoares.ms_produtos.domain.exception.ErrorMicrosericeException;
import io.github.hebertfsoares.ms_produtos.domain.infra.clients.ClientResource;
import io.github.hebertfsoares.ms_produtos.domain.repository.ProductRepository;
import io.github.hebertfsoares.ms_produtos.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ClientResource clientResource;
    private final ProductPublisher productPublisher;

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

        try{
            ProductForSales productForSales = new ProductForSales(savedProduct.getId(), savedProduct.getName());
            productPublisher.sendProductInfo(productForSales);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
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

    public Clients getClient(String cpf) throws DadosClientException, ErrorMicrosericeException {
        try {
            ResponseEntity<DataClient> dadosClients = clientResource.getClientByCpf(cpf);
            return Clients
                    .builder()
                    .client(dadosClients.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClientException();
            }
            throw new ErrorMicrosericeException(e.getMessage(), status);

        }
    }

}

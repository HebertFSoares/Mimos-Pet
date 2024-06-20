package io.github.hebertfsoares.ms_sales.domain.service;

import io.github.hebertfsoares.ms_sales.domain.entities.Client;
import io.github.hebertfsoares.ms_sales.domain.entities.Product;
import io.github.hebertfsoares.ms_sales.domain.entities.Sales;
import io.github.hebertfsoares.ms_sales.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_sales.domain.repository.ProductRepository;
import io.github.hebertfsoares.ms_sales.domain.repository.SalesRepository;
import io.github.hebertfsoares.ms_sales.dto.SalesRequest;
import io.github.hebertfsoares.ms_sales.dto.SalesResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SalesResponse registerSale(SalesRequest salesRequest) {
        Client client = clientRepository.findById(salesRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Product product = productRepository.findById(salesRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Sales sale = new Sales();
        sale.setClientId(salesRequest.getClientId());
        sale.setProductId(salesRequest.getProductId());
        sale.setQuantity(salesRequest.getQuantity());
        sale.setAmount(salesRequest.getAmount());
        sale.setPayment(salesRequest.getPayment());

        salesRepository.save(sale);

        return new SalesResponse(
                sale.getId(),
                sale.getProductId(),
                product.getName(),
                sale.getClientId(),
                client.getName(),
                sale.getDateSale(),
                sale.getQuantity(),
                sale.getAmount(),
                sale.getPayment()
        );
    }
}

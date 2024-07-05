package io.github.hebertfsoares.ms_sales.domain.service;

import io.github.hebertfsoares.ms_sales.domain.entities.Client;
import io.github.hebertfsoares.ms_sales.domain.entities.Product;
import io.github.hebertfsoares.ms_sales.domain.entities.Sales;
import io.github.hebertfsoares.ms_sales.domain.enums.Payment;
import io.github.hebertfsoares.ms_sales.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_sales.domain.repository.ProductRepository;
import io.github.hebertfsoares.ms_sales.domain.repository.SalesRepository;
import io.github.hebertfsoares.ms_sales.dto.SalesRequest;
import io.github.hebertfsoares.ms_sales.dto.SalesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SalesService salesService;

    private SalesRequest salesRequest;
    private Client client;
    private Product product;
    private Sales sale;

    @BeforeEach
    void setUp() {
        salesRequest = new SalesRequest(1L, 1L, 2, 100, Payment.CARD);
        client = new Client(1L, "Client Name");
        product = new Product(1L, "Product Name");
        sale = new Sales();
        sale.setId(1L);  // Setting the ID to simulate the saved entity
        sale.setClientId(salesRequest.getClientId());
        sale.setProductId(salesRequest.getProductId());
        sale.setQuantity(salesRequest.getQuantity());
        sale.setAmount(salesRequest.getAmount());
        sale.setPayment(salesRequest.getPayment());
        sale.setDateSale(LocalDateTime.now());
    }

    @Test
    void registerSaleSuccess() {
        when(clientRepository.findById(salesRequest.getClientId())).thenReturn(Optional.of(client));
        when(productRepository.findById(salesRequest.getProductId())).thenReturn(Optional.of(product));

        Sales savedSale = new Sales();
        savedSale.setId(1L); 
        savedSale.setClientId(salesRequest.getClientId());
        savedSale.setProductId(salesRequest.getProductId());
        savedSale.setQuantity(salesRequest.getQuantity());
        savedSale.setAmount(salesRequest.getAmount());
        savedSale.setPayment(salesRequest.getPayment());
        savedSale.setDateSale(LocalDateTime.now());

        when(salesRepository.save(any(Sales.class))).thenReturn(savedSale);

        SalesResponse response = salesService.registerSale(salesRequest);

        assertEquals(1L, response.getId());
        assertEquals(salesRequest.getProductId(), response.getProductId());
        assertEquals(product.getName(), response.getProductName());
        assertEquals(salesRequest.getClientId(), response.getClientId());
        assertEquals(client.getName(), response.getClientName());
        assertEquals(savedSale.getDateSale(), response.getDateSale());
        assertEquals(salesRequest.getQuantity(), response.getQuantity());
        assertEquals(salesRequest.getAmount(), response.getAmount());
        assertEquals(salesRequest.getPayment(), response.getPayment());

        verify(clientRepository, times(1)).findById(salesRequest.getClientId());
        verify(productRepository, times(1)).findById(salesRequest.getProductId());
        verify(salesRepository, times(1)).save(any(Sales.class));
    }

    @Test
    void registerSaleClientNotFound() {
        when(clientRepository.findById(salesRequest.getClientId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> salesService.registerSale(salesRequest));
        assertEquals("Client not found", exception.getMessage());

        verify(clientRepository, times(1)).findById(salesRequest.getClientId());
        verify(productRepository, times(0)).findById(salesRequest.getProductId());
        verify(salesRepository, times(0)).save(any(Sales.class));
    }

    @Test
    void registerSaleProductNotFound() {
        when(clientRepository.findById(salesRequest.getClientId())).thenReturn(Optional.of(client));
        when(productRepository.findById(salesRequest.getProductId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> salesService.registerSale(salesRequest));
        assertEquals("Product not found", exception.getMessage());

        verify(clientRepository, times(1)).findById(salesRequest.getClientId());
        verify(productRepository, times(1)).findById(salesRequest.getProductId());
        verify(salesRepository, times(0)).save(any(Sales.class));
    }
}

package io.github.hebertfsoares.ms_sales.web;

import io.github.hebertfsoares.ms_sales.domain.service.SalesService;
import io.github.hebertfsoares.ms_sales.dto.SalesRequest;
import io.github.hebertfsoares.ms_sales.dto.SalesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sales")
@AllArgsConstructor
@Tag(name = "Sales Controller", description = "Endpoints for managing sales")
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/register")
    @Operation(summary = "Register Sales", description = "Registers a new sales")
    public ResponseEntity<SalesResponse> registerSale(@RequestBody SalesRequest salesRequest) {
        SalesResponse salesResponse = salesService.registerSale(salesRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(salesResponse);
    }
}

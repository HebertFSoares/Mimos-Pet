package io.github.hebertfsiares.ms_client.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("clients")
@Tag(name = "Ping Controller", description = "Endpoints for pinging")
public class PingController {

    @GetMapping("/ping")
    @Operation(summary = "Ping", description = "Pings the server to check its status")
    public String Ping(){
        log.info("Obtendo o status do microservice de clientes");
        return "pong";
    }
}

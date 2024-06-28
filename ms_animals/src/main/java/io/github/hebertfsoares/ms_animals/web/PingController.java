package io.github.hebertfsoares.ms_animals.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("animals")
@RestController
@Tag(name = "Ping Controller", description = "Endpoints for pinging")
public class PingController {

    @GetMapping("ping")
    @Operation(summary = "Ping", description = "Pings the server to check its status")
    public String ping(){
        return "pong";
    }
}

package io.github.hebertfsiares.ms_client.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("clients")
public class PingController {

    @GetMapping("/ping")
    public String Ping(){
        log.info("Obtendo o status do microservice de clientes");
        return "pong";
    }
}

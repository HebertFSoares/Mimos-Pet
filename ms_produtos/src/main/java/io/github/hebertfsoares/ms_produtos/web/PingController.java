package io.github.hebertfsoares.ms_produtos.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class PingController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}

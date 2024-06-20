package io.github.hebertfsoares.ms_sales.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sales")
public class PingController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}

package io.github.hebertfsoares.ms_animals.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("animals")
@RestController
public class PingController {

    @GetMapping("ping")
    public String ping(){
        return "pong";
    }
}

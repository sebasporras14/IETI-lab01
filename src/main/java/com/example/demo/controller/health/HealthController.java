package com.example.demo.controller.health;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
public class HealthController {
    @GetMapping("/health")
    public String getHealth() {
        return "<h1>API working OK!</h1>";
    }
}
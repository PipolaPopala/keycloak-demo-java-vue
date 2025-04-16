package org.keycloak.keycloak_spring_boot_starter.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Это публичный эндпоинт";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "Это приватный эндпоинт";
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('admin')")
    public String adminTest() {
        return "Admin access granted!";
    }
}

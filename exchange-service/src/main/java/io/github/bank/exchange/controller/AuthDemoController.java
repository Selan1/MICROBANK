package io.github.bank.exchange.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthDemoController {

    @GetMapping("/auth/me")
    public Object getMe() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

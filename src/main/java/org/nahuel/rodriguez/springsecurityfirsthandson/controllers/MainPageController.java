package org.nahuel.rodriguez.springsecurityfirsthandson.controllers;

import org.nahuel.rodriguez.springsecurityfirsthandson.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    private final ProductService productService;

    public MainPageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/main")
    public String main(final Authentication authentication, final Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("products", productService.findAll());
        return "main.html";
    }
}

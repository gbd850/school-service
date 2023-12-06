package dev.peter.springdatajpapractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SpringDocController {
    @GetMapping("/swagger-ui")
    public RedirectView redirectToSpringDoc() {
        return new RedirectView("/swagger-ui/index.html");
    }
}

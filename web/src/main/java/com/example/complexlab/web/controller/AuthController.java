package com.example.complexlab.web.controller;

import com.example.complexlab.core.dto.RegisterRequest;
import com.example.complexlab.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest("", ""));
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult bindingResult, Model model) {
        if (userService.existsByUsername(registerRequest.username())) {
            bindingResult.rejectValue("username", "username.exists", "Username already exists");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerRequest", registerRequest);
            return "auth/register";
        }
        userService.register(registerRequest.username(), registerRequest.password());
        return "redirect:/auth/login";
    }
}

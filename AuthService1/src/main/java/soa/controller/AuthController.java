package soa.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import soa.model.User;
import soa.service.AuthService;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/api/auth/login")
    public Map<String, Object> login(
            @RequestBody Map<String, String> request) {

        String email = request.get("email");
        String password = request.get("password");

        String token = authService.login(email, password);

        return Map.of(
                "message", "Login successful",
                "token", token,
                "tokenType", "Bearer"
        );
    }
}
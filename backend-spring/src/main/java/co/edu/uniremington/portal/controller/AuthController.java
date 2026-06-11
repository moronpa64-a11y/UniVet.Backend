package co.edu.uniremington.portal.controller;

import co.edu.uniremington.portal.dto.auth.LoginRequest;
import co.edu.uniremington.portal.dto.auth.LoginResponse;
import co.edu.uniremington.portal.dto.auth.RegisterRequest;
import co.edu.uniremington.portal.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email != null) authService.forgotPassword(email);
        return ResponseEntity.ok(Map.of("message", "Si el correo existe, recibirás instrucciones"));
    }
}

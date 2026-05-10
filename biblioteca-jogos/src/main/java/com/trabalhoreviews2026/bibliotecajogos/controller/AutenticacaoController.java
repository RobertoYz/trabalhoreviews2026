package com.trabalhoreviews2026.bibliotecajogos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trabalhoreviews2026.bibliotecajogos.dto.LoginRequest;
import com.trabalhoreviews2026.bibliotecajogos.dto.LoginResponse;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    private static final String EMAIL_CORRETO = "usuario@esoft.com";
    private static final String SENHA_CORRETA = "Abc123";
    private static final String TOKEN_FIXO = "550e8400-e29b-41d4-a716-446655440000";

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (EMAIL_CORRETO.equals(loginRequest.getEmail()) &&
                SENHA_CORRETA.equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse(TOKEN_FIXO));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
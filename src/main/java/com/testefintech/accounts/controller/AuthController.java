package com.testefintech.accounts.controller;

import com.testefintech.accounts.dto.AuthenticationResponse;
import com.testefintech.accounts.dto.RegisterRequest;
import com.testefintech.accounts.dto.ResponseDTO;
import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.model.User;
import com.testefintech.accounts.repository.UserRepository;
import com.testefintech.accounts.service.AuthService;
import com.testefintech.accounts.service.JwtService; // <--- Importante
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // <--- O Lombok cria o construtor para TODOS os campos 'final' abaixo
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    // 1. DECLARAÇÃO DE TODAS AS DEPENDÊNCIAS (Faltavam estas)
    private final AuthService authService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // Seu arquivo se chama JwtService

    // (O construtor manual foi removido para o Lombok trabalhar sozinho)

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequest body) {
        Optional<User> user = this.repository.findByEmail(body.email());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Criando Usuário
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        newUser.setCpf(body.cpf());
        newUser.setBirthDate(body.birthDate());
        newUser.setDocumentFront(body.documentFront());
        newUser.setDocumentBack(body.documentBack());

        // Criando Conta
        Account newAccount = new Account();

        // CORREÇÃO: Usando BigDecimal para dinheiro
        newAccount.setBalance(new BigDecimal("100.00"));

        // Vi que seu Account.java tem 'owner', vamos preencher também
        newAccount.setOwner(newUser.getName());

        newAccount.setUser(newUser);
        newUser.setAccount(newAccount);

        // Salvando tudo
        this.repository.save(newUser);

        // Gerando Token
        String token = this.jwtService.generateToken(newUser.getEmail());

        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        String token = authService.login(email, password);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
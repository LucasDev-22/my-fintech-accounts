package com.testefintech.accounts.controller;

import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController // Define que esta classe é uma API que retorna JSON
@RequestMapping("/accounts") // Todas as rotas aqui começam com /accounts
public class AccountController {

    @Autowired
    private AccountService service;

    // Rota para criar uma conta: POST http://localhost:8080/accounts
    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account newAccount = service.create(account);
        return ResponseEntity.ok(newAccount);
    }

    // Rota para listar todas as contas: GET http://localhost:8080/accounts
    @GetMapping
    public List<Account> listAll() {
        return service.findAll();
    }
}
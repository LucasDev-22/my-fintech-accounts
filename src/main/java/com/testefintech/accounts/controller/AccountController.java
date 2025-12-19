package com.testefintech.accounts.controller;

import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define que esta classe é uma API que retorna JSON
@RequestMapping("/accounts") // Todas as rotas aqui começam com /accounts
public class AccountController {

    @Autowired
    private AccountService service;

    // Rota para criar uma conta: POST http://localhost:8080/accounts
    @PostMapping
    public Account create(@RequestBody Account account) {
        return service.createAccount(account);
    }

    // Rota para listar todas as contas: GET http://localhost:8080/accounts
    @GetMapping
    public List<Account> list() {
        return service.listAllAccounts();
    }
}
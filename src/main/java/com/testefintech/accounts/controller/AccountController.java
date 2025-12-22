package com.testefintech.accounts.controller;

import com.testefintech.accounts.dto.DashboardDTO;
import com.testefintech.accounts.dto.PixDTO;
import com.testefintech.accounts.dto.PixRequest;
import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard(Principal principal) {
        return ResponseEntity.ok(accountService.getDashboardData(principal.getName()));
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(Principal principal) {
        String email = principal.getName();
        return accountService.findBalanceByEmail(email);
    }

    @GetMapping
    public List<Account> getAll(){
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pix")
    public ResponseEntity<String> realizarPix(@RequestBody PixRequest request, Principal principal) {
        try {
            accountService.realizarPix(principal.getName(), request.valor(), request.destino());
            return ResponseEntity.ok("PIX realizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
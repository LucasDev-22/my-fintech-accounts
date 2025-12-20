package com.testefintech.accounts.controller;

import com.testefintech.accounts.dto.PixDTO;
import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    public List<Account> getAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pix")
    public ResponseEntity<String> realizarPix(@RequestBody PixDTO pixDTO) {
        try {
            service.realizarPix(pixDTO.accountId(), pixDTO.valor(), pixDTO.destino());
            return ResponseEntity.ok("PIX realizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
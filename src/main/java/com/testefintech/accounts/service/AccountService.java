package com.testefintech.accounts.service;

import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    // Criar uma nova conta
    public Account create(Account account) {
        return repository.save(account);
    }

    // Listar todas as contas
    public List<Account> findAll() {
        return repository.findAll();
    }

    // Buscar uma conta por ID
    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }
}
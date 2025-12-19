package com.testefintech.accounts.service;

import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service // Avisa ao Spring que aqui fica a lógica de negócio
public class AccountService {

    @Autowired // O Spring "instala" o repositório aqui automaticamente
    private AccountRepository repository;

    // Método para criar uma nova conta
    public Account createAccount(Account account) {
        // Regra de Negócio: Toda conta nova começa com saldo ZERO se não for informado
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        // Regra de Negócio: Não aceitamos nomes vazios
        if (account.getHolderName() == null || account.getHolderName().isEmpty()) {
            throw new RuntimeException("O nome do titular é obrigatório!");
        }

        return repository.save(account);
    }

    // Método para listar todas as contas (bom para testes)
    public List<Account> listAllAccounts() {
        return repository.findAll();
    }
}
package com.testefintech.accounts.service;

import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.model.Transaction;
import com.testefintech.accounts.repository.AccountRepository;
import com.testefintech.accounts.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Lógica para realizar o PIX
    @Transactional
    public void realizarPix(Long accountId, BigDecimal valor, String destino) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        // CORREÇÃO: Use getBalance() pois o campo na sua Model é balance
        if (account.getBalance().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        account.setBalance(account.getBalance().subtract(valor));
        repository.save(account);

        Transaction tx = new Transaction();
        tx.setIcon("send");
        tx.setLoja("PIX para " + destino);
        tx.setValor(valor.negate());
        tx.setData(LocalDateTime.now());
        tx.setAccount(account);

        transactionRepository.save(tx);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }
}
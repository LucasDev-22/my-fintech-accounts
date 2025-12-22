package com.testefintech.accounts.service;

import com.testefintech.accounts.dto.DashboardDTO;
import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.model.Transaction;
import com.testefintech.accounts.model.User;
import com.testefintech.accounts.repository.AccountRepository;
import com.testefintech.accounts.repository.TransactionRepository;
import com.testefintech.accounts.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public BigDecimal findBalanceByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user.getAccount().getBalance();
    }

    public DashboardDTO getDashboardData(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Account account = user.getAccount();

        List<Transaction> transactions = transactionRepository.findByAccount(account);

        return new DashboardDTO(account.getBalance(), transactions);
    }

    // Lógica para realizar o PIX
    @Transactional
    public void realizarPix(String emailPagador, BigDecimal valor, String destino) {
        User user = userRepository.findByEmail(emailPagador)
                .orElseThrow(() -> new RuntimeException("Pagador não encontrado"));
        Account contaPagador = user.getAccount();

        if (contaPagador.getBalance().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        contaPagador.setBalance(contaPagador.getBalance().subtract(valor));
        repository.save(contaPagador);

        Transaction tx = new Transaction();
        tx.setIcon("send");
        tx.setLoja("PIX enviado para " + destino);
        tx.setValor(valor.negate());
        tx.setData(LocalDateTime.now());
        tx.setAccount(contaPagador);
        transactionRepository.save(tx);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }
}
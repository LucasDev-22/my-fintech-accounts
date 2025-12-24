package com.testefintech.accounts.service;

import com.testefintech.accounts.dto.DashboardDTO;
import com.testefintech.accounts.model.Account;
import com.testefintech.accounts.model.PixKey;
import com.testefintech.accounts.model.Transaction;
import com.testefintech.accounts.model.User;
import com.testefintech.accounts.repository.AccountRepository;
import com.testefintech.accounts.repository.PixKeyRepository;
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

    @Autowired
    private PixKeyRepository pixKeyRepository;

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
    public void realizarPix(String emailPagador, java.math.BigDecimal valor, String chavePixDestino) {
        User usuarioPagador = userRepository.findByEmail(emailPagador)
                .orElseThrow(() -> new RuntimeException("Pagador não encontrado"));
        Account contaPagador = usuarioPagador.getAccount();

        PixKey pixKeyEncontrada = pixKeyRepository.findByChave(chavePixDestino)
                .orElseThrow(() -> new RuntimeException("Destinatário não encontrado!"));
        Account contaDestino = pixKeyEncontrada.getAccount();
        User usuarioDestino = contaDestino.getUser();

        if (contaPagador.getBalance().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }
        if (contaPagador.getId().equals(contaDestino.getId())) {
            throw new RuntimeException("Não pode fazer PIX para você mesmo!");
        }

        contaPagador.setBalance(contaPagador.getBalance().subtract(valor));
        contaDestino.setBalance(contaDestino.getBalance().add(valor));

        repository.save(contaPagador);
        repository.save(contaDestino);

        Transaction txPagador = new Transaction();
        txPagador.setIcon("send");
        txPagador.setLoja("PIX enviado para " + usuarioDestino.getName());
        txPagador.setValor(valor.negate());
        txPagador.setData(LocalDateTime.now());
        txPagador.setAccount(contaPagador);
        transactionRepository.save(txPagador);

        Transaction txDestino = new Transaction();
        txDestino.setIcon("attach_money");
        txDestino.setLoja("PIX recebido de " + usuarioPagador.getName());
        txDestino.setValor(valor);
        txDestino.setData(LocalDateTime.now());
        txDestino.setAccount(contaDestino);
        transactionRepository.save(txDestino);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }
}
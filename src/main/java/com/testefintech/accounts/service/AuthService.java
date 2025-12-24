package com.testefintech.accounts.service;

import com.testefintech.accounts.dto.AuthenticationResponse;
import com.testefintech.accounts.dto.RegisterRequest;
import com.testefintech.accounts.model.PixKey;
import com.testefintech.accounts.model.PixKeyType;
import com.testefintech.accounts.repository.AccountRepository;
import com.testefintech.accounts.model.User;
import com.testefintech.accounts.repository.PixKeyRepository;
import com.testefintech.accounts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(this.passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(email);
        } else {
            throw new RuntimeException("Senha inválida");
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PixKeyRepository pixKeyRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        var savedUser = userRepository.save(user);

        var account = new com.testefintech.accounts.model.Account();
        account.setOwner(savedUser.getName());
        account.setBalance(new java.math.BigDecimal("100.00")); // Bônus de R$ 100,00
        account.setUser(savedUser);

        accountRepository.save(account);

        PixKey emailKey = new PixKey();
        emailKey.setChave(savedUser.getEmail());
        emailKey.setTipo(PixKeyType.EMAIL);
        emailKey.setAccount(account);

        pixKeyRepository.save(emailKey);

        var jwtToken = jwtService.generateToken(savedUser.getEmail());
        return new AuthenticationResponse(jwtToken);
    }
}

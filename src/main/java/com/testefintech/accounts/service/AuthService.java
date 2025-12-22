package com.testefintech.accounts.service;

import com.testefintech.accounts.service.JwtService;
import com.testefintech.accounts.model.User;
import com.testefintech.accounts.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}

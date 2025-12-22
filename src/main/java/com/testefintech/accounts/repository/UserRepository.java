package com.testefintech.accounts.repository;

import com.testefintech.accounts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método vital: buscar o usuário pelo e-mail para validar a senha
    Optional<User> findByEmail(String email);

    String email(String email);
}

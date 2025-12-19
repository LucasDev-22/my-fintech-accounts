package com.testefintech.accounts.repository;

import com.testefintech.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Aqui o Spring Data JPA faz a "mágica":
    // Ele já cria sozinho os métodos de Salvar, Deletar e Buscar por ID!
}

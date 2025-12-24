package com.testefintech.accounts.repository;

import com.testefintech.accounts.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, Long> {
    Optional<PixKey> findByChave(String chave);

    boolean existsByChave(String chave);
}

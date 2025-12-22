package com.testefintech.accounts.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Relação: Um usuário tem uma conta bancária
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;
}

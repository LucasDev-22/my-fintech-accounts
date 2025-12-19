package com.testefintech.accounts.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity // Diz ao Java que isso é uma tabela no banco de dados
@Table(name = "tb_accounts")
@Data // O Lombok cria Getters e Setters automaticamente para nós
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private BigDecimal balance; // Saldo (usamos BigDecimal pra dinheiro, nunca Float ou Double!)

}
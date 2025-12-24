package com.testefintech.accounts.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_pix_keys")
@Data
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chave;

    @Enumerated(EnumType.STRING)
    private PixKeyType tipo;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}

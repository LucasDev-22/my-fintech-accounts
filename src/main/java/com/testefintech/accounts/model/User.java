package com.testefintech.accounts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    // Relação: Um usuário tem uma conta bancária
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;
}

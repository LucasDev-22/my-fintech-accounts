package com.testefintech.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(unique = true)
    private String cpf;

    private LocalDate birthDate;

    @Column(columnDefinition = "TEXT")
    private String documentFront;

    @Column(columnDefinition = "TEXT")
    private String documentBack;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    // Relação: Um usuário tem uma conta bancária
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;
}

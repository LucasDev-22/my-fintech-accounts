# 🏦💸 BOSS BANK - Backend API (MyFintech) ☕

Este é o motor de regras de negócios e segurança do **BOSS BANK**, uma API REST robusta construída com Java e Spring Boot para gerenciar contas, autenticação e transações financeiras em tempo real.

> **Status do Projeto:** Fase 3 - Segurança JWT, Banco de Dados e Lógica de Negócios (Concluído) ✅

## 🛠️ Tecnologias Utilizadas ⚙️
* **Java 21** (LTS)
* **Spring Boot 3.4.1**
* **Spring Security + JWT** (Autenticação Stateless)
* **PostgreSQL** (Banco de Dados Relacional)
* **Docker & Docker Compose** (Containerização)
* **Lombok** (Produtividade)

## ⚙️ Funcionalidades

* [x] Cadastro de Usuário com KYC (CPF, Upload de Documentos em Base64).
* [x] Autenticação via Token JWT.
* [x] Criação automática de Conta Bancária.
* [x] Gestão de Saldo com `BigDecimal` (Alta precisão monetária).
* [ ] Transações PIX (Em breve).
* [ ] Gestão de Cartões de Crédito (Em breve).

## 🛠️ Como Rodar

1.  Suba o banco de dados:
    ```bash
    docker compose up -d
    ```
2.  Execute a aplicação Spring Boot via IntelliJ ou Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
4.  A API estará rodando em `http://localhost:8080`.

Desenvolvido por **Lucas Gabriel**
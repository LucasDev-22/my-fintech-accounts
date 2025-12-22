# 🏦💸 BOSS BANK - Backend API (MyFintech) ☕

Este é o motor de regras de negócios e segurança do **BOSS BANK**, uma API REST robusta construída com Java e Spring Boot para gerenciar contas, autenticação e transações financeiras em tempo real.

> **Status do Projeto:** Fase 3 - Segurança JWT, Banco de Dados e Lógica de Negócios (Concluído) ✅

## 🛠️ Tecnologias Utilizadas ⚙️
* **Java 21** & **Spring Boot 3**
* **Spring Security**: Camada de proteção contra ataques e controle de acesso.
* **JWT (JSON Web Token)**: Autenticação Stateless segura.
* **Spring Data JPA**: Abstração de persistência de dados.
* **PostgreSQL 15**: Banco de dados relacional (Docker).
* **Lombok**: Redução de boilerplate code.
* **Docker Compose**: Orquestração do ambiente de banco de dados.

## 🏗️ Arquitetura de Segurança
O projeto implementa uma cadeia de filtros de segurança (`SecurityFilterChain`) que:
1.  **Intercepta** todas as requisições HTTP.
2.  **Verifica** a presença e validade do Token JWT no Header `Authorization`.
3.  **Identifica** o usuário (ex: Lucas) e libera o acesso aos dados apenas dele.
4.  **Bloqueia** (403 Forbidden) qualquer tentativa de acesso não autorizado.

## 📌 Endpoints Principais

### 🔐 Autenticação (Públicos)
* `POST /auth/login`: Recebe e-mail/senha e retorna um **Token JWT** válido.
* `POST /auth/register`: (Em desenvolvimento) Criação de novos correntistas.

### 💰 Conta & Transações (Protegidos)
> *Requer Header: `Authorization: Bearer <seu_token>`*

* `GET /accounts/dashboard`: Retorna o **Saldo** atual e a lista de **Últimas Transações** do usuário logado.
* `POST /accounts/pix`: Realiza transferência bancária.
    * *Novo Payload (Seguro):* `{"valor": 50.00, "destino": "Cafeteria"}`
    * *Nota:* O pagador é identificado automaticamente pelo Token, eliminando fraudes de ID.

## 🚀 Como Executar o Backend
1.  Certifique-se de que o **Docker** está rodando.
2.  Suba o banco de dados:
    ```bash
    docker compose up -d
    ```
3.  Execute a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```
4.  A API estará rodando em `http://localhost:8080`.

## 🤝 Parceria Fullstack
Desenvolvido por **Lucas Gabriel** em parceria estratégica com a **Gemini IA**, focando em clean code e arquitetura resiliente.
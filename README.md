# 💸 MyFintech API - Sistema de Microsserviços para Carteira Digital

Este projeto evoluiu de um simples microsserviço de contas para uma plataforma funcional de transferências via PIX, com persistência real de dados.

> **Status do Projeto:** Em desenvolvimento 🚀
> **Fase 2:** Transações e Persistência (Concluído: Débito em Conta e Integração Front/Back)

## 🛠️ Tecnologias Utilizadas

* **Java 21** & **Spring Boot 3**
* **Spring Data JPA**
* **PostgreSQL 15** (Rodando via Docker)
* **pgAdmin 4** (Interface visual para o banco)
* **Lombok** & **Maven**
* **Docker & Docker Compose**

## 🏗️ Arquitetura

O projeto segue o padrão de **Camadas**:
1.  **Controller:** Porta de entrada da API (REST).
2.  **Service:** Onde residem as regras de negócio.
3.  **Repository:** Interface de comunicação com o banco de dados.
4.  **Model:** Definição das entidades de dados.

## 🚀 Como Executar o Projeto

1.  Clone o repositório.
2.  Certifique-se de ter o **Docker** instalado.
3.  Na raiz do projeto, suba o banco de dados:
    ```bash
    docker compose up -d
    ```
4.  Execute a aplicação via terminal ou IDE:
    ```bash
    ./mvnw spring-boot:run
    ```
5.  A API estará disponível em.
    ```bash
    http://localhost:8080/accounts
    ```

## ⚙️ O que já funciona?
1.  **Criação e Consulta de Contas:** Gerenciamento de saldo inicial.
2.  **Motor de PIX:** Lógica de negócio para validar saldo e realizar débitos automáticos.
3.  **Arquitetura Reativa:** Interface Angular que atualiza o saldo sem necessidade de recarregar a página.

## 📌 Endpoints Atualizados
* `GET /accounts/{id}`: Consulta detalhada da conta e saldo atual.
* `POST /accounts/pix`: Realiza uma transferência (Débito e Registro de Transação).
    * *Payload:* `{"accountId": 1, "valor": 1000.00, "destino": "Boss Burguer"}`

## 🤝 Parceria Fullstack
Desenvolvido por **Lucas Gabriel** em parceria estratégica com a **Gemini IA**, focando em clean code e arquitetura resiliente.
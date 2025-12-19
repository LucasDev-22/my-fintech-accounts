# 💸 MyFintech API - Sistema de Microsserviços para Carteira Digital

Este projeto é o ponto de partida de uma arquitetura de microsserviços voltada para o setor financeiro. O objetivo é construir um ecossistema escalável para gerenciamento de contas e transações bancárias.

> **Status do Projeto:** Em desenvolvimento 🚀 (Fase 1: Core de Contas)

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3**
* **Spring Data JPA**
* **Banco de Dados H2** (Em memória para desenvolvimento rápido)
* **Lombok** (Para produtividade e código limpo)
* **Maven** (Gerenciador de dependências)

## 🏗️ Arquitetura

O projeto segue o padrão de **Camadas**:
1.  **Controller:** Porta de entrada da API (REST).
2.  **Service:** Onde residem as regras de negócio.
3.  **Repository:** Interface de comunicação com o banco de dados.
4.  **Model:** Definição das entidades de dados.

## 🚀 Como Executar o Projeto

1.  Clone o repositório.
2.  Certifique-se de ter o **JDK 21** instalado.
3.  Importe o projeto em sua IDE (IntelliJ, Eclipse ou VS Code).
4.  Execute a classe `AccountsApplication.java`.
5.  A API estará disponível em `http://localhost:8080/accounts`.

## 📌 Endpoints Iniciais

* `POST /accounts`: Cria uma nova conta.
    * *Payload:* `{"holderName": "Seu Nome", "balance": 100.00}`
* `GET /accounts`: Lista todas as contas cadastradas.

## 🤝 Colaboradores
Este projeto está sendo desenvolvido com o apoio e mentoria da **Gemini IA**, atuando como parceira fullstack na definição de arquitetura e boas práticas.

---
Desenvolvido por [ Lucas Gabriel ]
# 🏦 BOSS BANK - Frontend (FintechA) 🚀

Este é o módulo de interface do usuário do **BOSS BANK**, desenvolvido com as tecnologias mais modernas do ecossistema Angular para proporcionar uma experiência financeira fluida, segura e reativa.

> **Status do Projeto:** Fase 3 - Segurança JWT, Integração Real e AuthGuard (Concluído) ✅

## 🛠️ Tecnologias Utilizadas
* **Angular 18+**: Utilizando **Signals** para gerenciamento de estado reativo de altíssima performance.
* **Angular Material**: Componentes de UI modernos e acessíveis.
* **HTTP Interceptors**: Injeção automática de tokens JWT em todas as requisições.
* **AuthGuard**: Proteção de rotas (Dashboard inacessível sem login).
* **SCSS**: Estilização avançada e modular.

## ✨ Funcionalidades Implementadas
1.  **Autenticação Segura**: Tela de Login integrada com Spring Security via Tokens JWT.
2.  **Dashboard Vivo**: Saldo e extrato consumidos diretamente do banco de dados PostgreSQL.
3.  **Proteção de Rotas**: Redirecionamento automático para login caso o token seja inválido ou inexistente.
4.  **Sistema de PIX Real**: O valor debitado reflete instantaneamente no banco de dados e atualiza a interface via Signals.
5.  **Extrato Dinâmico**: Histórico de transações persistente, trazido do backend em tempo real.

## 🚀 Como Executar o Front-end

### Pré-requisitos
* Node.js (versão 18 ou superior)
* Angular CLI instalado globalmente (`npm install -g @angular/cli`)

### Instalação
1. Entre na pasta do projeto:
   ```bash
   cd fintech_a
   npm install
   ng serve

2. Acesse http://localhost:4200 (Você será redirecionado para o Login).

Desenvolvido por Lucas Gabriel com suporte estratégico da Gemini IA.

import {Injectable, signal} from '@angular/core';

export interface Transaction {
  id: number;
  icon: string;
  loja: string;
  valor: number;
  data: string;
}

@Injectable({
  providedIn: 'root',
})
export class Account {
  // Estado centralizado do saldo e transações
  private _saldo = signal(15250.85);
  private _transacoes = signal<Transaction[]>([
    { id: 1, icon: 'shopping_cart', loja: 'Supermercado Silva', valor: -150.20, data: 'Hoje' },
    { id: 2, icon: 'restaurant', loja: 'Restaurante Sabor', valor: -45.00, data: 'Ontem' },
    { id: 3, icon: 'payments', loja: 'Depósito Recebido', valor: 1200.00, data: '18 Dez' },
  ]);

  // Expondo os sinais como leitura (read-only)
  saldo = this._saldo.asReadonly();
  transacoes = this._transacoes.asReadonly();

  realizarPix(valor: number, destino: string) {
    // 1. Atualiza o saldo
    this._saldo.update(atual => atual - valor);

    // 2. Adiciona à lista de transações
    const novaTransacao: Transaction = {
      id: Date.now(),
      icon: 'send',
      loja: `PIX para ${destino}`,
      valor: -valor,
      data: 'Agora'
    };
    this._transacoes.update(lista => [novaTransacao, ...lista]);
  }
}

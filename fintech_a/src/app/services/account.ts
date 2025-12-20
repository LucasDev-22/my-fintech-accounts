import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

// 1. Definimos as interfaces aqui mesmo para evitar o erro de importação por enquanto
export interface Transaction {
  id: number;
  icon: string;
  loja: string;
  valor: number;
  data: string;
}

export interface AccountData { // Mudamos o nome para não conflitar com a classe
  id: number;
  owner: string;
  balance: number;
}

@Injectable({
  providedIn: 'root',
})
export class AccountService { // Nome correto: AccountService
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/accounts';

  private _saldo = signal<number>(0);
  private _transacoes = signal<Transaction[]>([]);

  saldo = this._saldo.asReadonly();
  transacoes = this._transacoes.asReadonly();

  carregarDados() {
    this.http.get<any>(`${this.API_URL}/1`).subscribe(res => {
      this._saldo.set(res.balance);
      if (res.transactions) {
        this._transacoes.set(res.transactions);
      }
    })
  }

  realizarPix(valor: number, destino: string) {
    const pixData = {
      accountId: 1,
      valor: valor,
      destino: destino
    };

    this.http.post(`${this.API_URL}/pix`, pixData, { responseType: 'text' })
      .subscribe(() => {
        this.carregarDados();
      });
  }
}

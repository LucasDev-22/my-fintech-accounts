import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Transaction {
  id: number;
  icon: string;
  loja: string;
  valor: number;
  data: string;
}

interface DashboardResponse {
  balance: number;
  transactions: Transaction[];
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
    this.http.get<DashboardResponse>(`${this.API_URL}/dashboard`).subscribe({
      next: (data) => {
        this._saldo.set(data.balance);
        this._transacoes.set(data.transactions);
      },
      error: (err) => console.error('Erro ao dashboard', err)
    });
  }

  realizarPix(valor: number, destino: string) {
    const pixData = { valor, destino };
    this.http.post(`${this.API_URL}/pix`, pixData, { responseType: 'text' })
      .subscribe(() => {
        this.carregarDados();
      })
  }
}

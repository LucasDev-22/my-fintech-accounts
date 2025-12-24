import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Transaction {
  id: number;
  icon: string;
  loja: string;
  valor: number;
  data: string;
}

export interface UserUpdateRequest {
  name: string;
  phone: string;
  profileImage: string;
}

interface DashboardResponse {
  balance: number;
  transactions: Transaction[];
}

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private http = inject(HttpClient);

  // URL base para contas
  private readonly API_URL = 'http://localhost:8080/accounts';
  private readonly USER_API_URL = 'http://localhost:8080/users';

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
      error: (err) => console.error('Erro ao carregar dashboard', err)
    });
  }

  realizarPix(valor: number, destino: string) {
    const pixData = { valor, destino };
    this.http.post(`${this.API_URL}/pix`, pixData, { responseType: 'text' })
      .subscribe(() => {
        this.carregarDados();
      });
  }

  updateUser(email: string, dados: UserUpdateRequest) {
    return this.http.put(`${this.USER_API_URL}/update?email=${email}`, dados);
  }

  getUser(email: string) {
    return this.http.get<UserUpdateRequest>(`${this.USER_API_URL}/me?email=${email}`);
  }
}

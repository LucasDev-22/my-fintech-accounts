import { Component, inject, signal, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PixModal } from '../components/pix-modal/pix-modal';
import { AccountService } from '../services/account';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { MatDivider } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { Router } from '@angular/router';
import { SidebarComponent } from '../components/sidebar/sidebar.component';

interface DashboardResponse {
  balance: number;
  transactions: Transferable[];
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatCardModule, MatIconModule, MatButtonModule, MatDialogModule, CommonModule, CurrencyPipe, MatMenuTrigger, MatMenu, MatDivider, MatMenuModule, SidebarComponent],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  private accountService = inject(AccountService);
  private dialog = inject(MatDialog);
  private router = inject(Router);

  saldo = this.accountService.saldo;
  recentes = this.accountService.transacoes;
  exibirSaldo = signal(true);

  ngOnInit() {
    this.accountService.carregarDados();
  }

  toggleSaldo(){
    this.exibirSaldo.update(v => !v);
  }

  abrirPagamentoPix() {
    this.dialog.open(PixModal, {
      width: '450px',
      panelClass: 'custom-modal'
    });
  }

  sair() {
    localStorage.removeItem('auth-token'); // Limpa o token
    this.router.navigate(['/login']);      // Chuta pro login
  }

}

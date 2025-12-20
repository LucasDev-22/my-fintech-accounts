import { Component, inject, signal, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PixModal } from '../components/pix-modal/pix-modal';
import { AccountService } from '../services/account';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatCardModule, MatIconModule, MatButtonModule, MatDialogModule, CommonModule, CurrencyPipe],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  private accountService = inject(AccountService);
  private dialog = inject(MatDialog);

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

}

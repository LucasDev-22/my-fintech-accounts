import { Component, inject, signal, OnInit, PLATFORM_ID } from '@angular/core'; // <--- Adicione PLATFORM_ID
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule, CurrencyPipe, isPlatformBrowser } from '@angular/common'; // <--- Adicione isPlatformBrowser
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PixModal } from '../components/pix-modal/pix-modal';
import { AccountService, UserUpdateRequest, Transaction } from '../services/account';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { MatDivider } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { Router } from '@angular/router';
import { SidebarComponent } from '../components/sidebar/sidebar.component';
import { EditProfileModalComponent } from '../components/edit-profile-modal/edit-profile-modal';

interface DashboardResponse {
  balance: number;
  transactions: Transaction[];
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
  private platformId = inject(PLATFORM_ID);

  userData = signal<UserUpdateRequest>({
    name: '',
    phone: '',
    profileImage: ''
  });

  saldo = this.accountService.saldo;
  recentes = this.accountService.transacoes;
  exibirSaldo = signal(true);

  currentUserEmail = '';

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {

      const savedEmail = localStorage.getItem('user-email');

      if (!savedEmail) {
        this.router.navigate(['/login']);
        return;
      }

      this.currentUserEmail = savedEmail;
      console.log('E-mail recuperado:', this.currentUserEmail);

      this.accountService.carregarDados();
      this.carregarPerfil();
    }
  }

  carregarPerfil() {
    this.accountService.getUser(this.currentUserEmail).subscribe({
      next: (data) => {
        this.userData.set(data);
      },
      error: (err) => console.error('Erro ao buscar usuário:', err)
    });
  }

  editarPerfil() {
    const dialogRef = this.dialog.open(EditProfileModalComponent, {
      width: '450px',
      data: { ...this.userData() }
    });

    dialogRef.afterClosed().subscribe((result: UserUpdateRequest | undefined) => {
      if (result) {
        this.userData.set(result);

        this.accountService.updateUser(this.currentUserEmail, result).subscribe({
          next: () => {
            alert('Perfil atualizado! ✅');
          },
          error: (err) => {
            console.error(err);
            alert('Erro ao salvar.');
            this.carregarPerfil();
          }
        });
      }
    });
  }

  toggleSaldo(){
    this.exibirSaldo.update(v => !v);
  }

  abrirPagamentoPix() {
    this.dialog.open(PixModal, { width: '450px', panelClass: 'custom-modal' });
  }

  sair() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.clear();
    }
    this.router.navigate(['/login']);
  }
}

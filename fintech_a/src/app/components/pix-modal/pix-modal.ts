import { Component, inject } from '@angular/core';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { Account } from '../../services/account';

@Component({
  selector: 'app-pix-modal',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatFormFieldModule, MatInputModule, FormsModule],
  template: `
    <h2 mat-dialog-title>💸 Enviar PIX</h2>
    <mat-dialog-content>
      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Destino (CPF/CNPJ, E-mail, Telefone, Chave Aleatória)</mat-label>
        <input matInput [(ngModel)]="destino">
      </mat-form-field>

      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Valor</mat-label>
        <input matInput
               type="number"
               [(ngModel)]="valor"
               placeholder="0.00"
               (keydown)="apenasNumeros($event)">
      </mat-form-field>
    </mat-dialog-content>

    <mat-dialog-actions align="end">
      <button mat-button (click)="fechar()">Cancelar</button>
      <button mat-raised-button color="primary"
              (click)="confirmar()"
              [disabled]="!valor || valor <= 0 || !destino">
        Confirmar Pagamento
      </button>
    </mat-dialog-actions>
  `,
  styles: `.full-width { width: 100%; margin-top: 10px; }`
})
export class PixModal {
  private accountService = inject(Account);
  private dialogRef = inject(MatDialogRef<PixModal>);

  // Inicia como undefined para o input ficar vazio (sem o 0)
  valor: number | undefined;
  destino: string = '';

  // Função para bloquear letras e caracteres especiais no input de valor
  apenasNumeros(event: KeyboardEvent) {
    const permitidos = ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete', '.', ','];
    if (permitidos.includes(event.key)) return;

    // Bloqueia se não for número
    if (isNaN(Number(event.key)) || event.code === 'Space') {
      event.preventDefault();
    }
  }

  confirmar() {
    if (this.valor && this.valor > 0 && this.destino) {
      this.accountService.realizarPix(this.valor, this.destino);
      this.dialogRef.close();
    }
  }

  fechar() {
    this.dialogRef.close();
  }
}

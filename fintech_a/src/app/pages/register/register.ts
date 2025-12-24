import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {AuthService, RegisterData} from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule, FormsModule, RouterLink, MatCardModule,
    MatFormFieldModule, MatInputModule, MatButtonModule,
    MatDatepickerModule, MatNativeDateModule, MatIconModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  private authService = inject(AuthService);
  private router = inject(Router);

  form = {
    name: '',
    email: '',
    password: '',
    cpf: '',
    birthDate: '',
    documentFront: '',
    documentBack: ''
  };

  processFile(event: any, field: 'documentFront' | 'documentBack') {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.form[field] = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  fazerCadastro() {
    if (!this.form.name || !this.form.email || !this.form.cpf || !this.form.documentFront) {
      alert('Preencha todos os campos!');
      return;
    }

    const dataToSend: RegisterData = {
      ...this.form,
      birthDate: this.form.birthDate
    };

    this.authService.register(dataToSend).subscribe({
      next: () => {
        localStorage.setItem('user-email', this.form.email);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao cadastrar. Verifique os dados.');
      }
    });
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { tap } from 'rxjs/operators';

export interface RegisterData {
  name: string;
  email: string;
  password: string;
  cpf: string;
  birthDate: string;
  documentFront: string;
  documentBack: string;
}

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private http = inject(HttpClient);
  private platformId = inject(PLATFORM_ID);

  register(data: RegisterData) {
    return this.http.post<{ token: string }>(`${this.apiUrl}/register`, data).pipe(
      tap(response => {
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('auth-token', response.token);
        }
      })
    );
  }

  login(email: string, password: string) {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap(response => {
          if (isPlatformBrowser(this.platformId)) {
            localStorage.setItem('auth-token', response.token);
          }
        })
      );
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('auth-token');
      localStorage.removeItem('user-email');
    }
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)){
      return !!localStorage.getItem('auth-token');
    }
    return false;
  }
}

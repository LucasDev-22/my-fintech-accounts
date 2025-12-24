import { Component, signal } from '@angular/core';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true, // Certifique-se de que isso está aqui se for Angular 18/19
  imports: [RouterOutlet], // <-- Tudo dentro do mesmo array
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('fintech_a');
}

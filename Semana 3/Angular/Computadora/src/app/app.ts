import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Computadora');

  //Creamos el constructor y inyectamos el servicio de rutas
  constructor(private router: Router) {}

  //Método para navegar a la ruta listar del componente Listar
  listarCompu() {
    this.router.navigate(['lista']);
  }

  guardarCompu() {
    this.router.navigate(['guardar']);
  }

  editarcompu () {
    this.router.navigate(['editar']);
  }

}

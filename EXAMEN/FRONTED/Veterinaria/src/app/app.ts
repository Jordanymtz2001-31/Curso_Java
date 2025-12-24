import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Veterinaria');

  //Set para controlar múltiples menús abiertos simultáneamente
  //Puede contener varios valores de tipo string que representan los menús abiertos
  protected openMenus = new Set<string>();

  //Creamos el construcctor para inyectar la dependencias de router
  constructor(private router: Router) {}

  //Método para alternar menús
  //Si ago clic en un menú y está abierto, lo cierra
  //Si ago clic en un menú y está cerrado, lo abre
  toggleMenu(menu: string) {
    if (this.openMenus.has(menu)) { //Si el menu esta abierto
      this.openMenus.delete(menu); //Lo cerramos
    } else {
      this.openMenus.add(menu); //Lo abrimos
    }
  }

  //Método para verificar si un menú está abierto
  isMenuOpen(menu: string): boolean {
    return this.openMenus.has(menu);
  }

//Creamos los metodos de navegacion que se usaran en el template de app.html para navegar entre las diferentes vistas

  //LISTAR--------------------------------------------------------------------------------------
  listarCliente() {
    this.router.navigate(['listar/clientes']);
  }

  listarMascotas(){
    this.router.navigate(['listar/mascotas'])
  }

  listarResponsables(){
    this.router.navigate(['listar/responsables'])
  }

  listarVeterinarias(){
    this.router.navigate(['listar/veterinarias'])
  }

  //GUARDAR------------------------------------------------------------------------------------
  guardarCliente(){
    this.router.navigate(['guardar/cliente'])
  }

  guardarMascota(){
    this.router.navigate(['guardar/mascota'])
  }

  guardarResponsable(){
    this.router.navigate(['guardar/responsable'])
  }

  guardarVeterinaria(){
    this.router.navigate(['guardar/veterinaria'])
  }

  //EDITAR---------------------------------------------------------------------------------
  editarCliente(){
    this.router.navigate(['editar/cliente'])
  }

  editarMascota(){
    this.router.navigate(['editar/mascota'])
  }

  editarResponsable(){
    this.router.navigate(['editar/responsable'])
  }

  editarVeterinaria(){
    this.router.navigate(['editar/veterinaria'])
  }

  //DETALLES-------------------------------------------------------------------------------------

  //Detalle del Cliente para mostrar sus mascotas
  detalleCliente(){
    this.router.navigate(['detalle/cliente'])
  }

  //Detalle de Responsable para mostrar las mascotas acardo de el responsable
  detalleResponsable(){
    this.router.navigate(['detalle/responsable'])
  }

  //Detalles de la Veterinaria para mostrar la lista de mascotas y responsables que tiene
  detalleVeterinaria(){
    this.router.navigate(['detalle/veterinaria'])
  }

}


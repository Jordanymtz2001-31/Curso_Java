import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('SuperMercado');

  //Set para controlar múltiples menús abiertos simultáneamente
  //Puede contener varios valores de tipo string que representan los menús abiertos
  protected openMenus = new Set<string>();

  //Creamos un contructor para inicializar el componente 
  //Y inyectamos los servicios necesarios
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
  listarTiendas() {
    this.router.navigate(['listar-tiendas']);
  }

  listarProductos() {
    this.router.navigate(['listar-productos']);
  }

  listarClientes() {
    this.router.navigate(['listar-clientes']);
  }

  listarEmpleados() {
    this.router.navigate(['listar-empleados']);
  }

  listarDepartamentos() {
    this.router.navigate(['listar-departamentos']);
  }

  listaProveedores() {
    this.router.navigate(['listar-proveedores']);
  }

  //GUARDAR-------------------------------------------------------------------------------------
  guardarTiendas() {
    this.router.navigate(['guardar-tiendas']);
  }

  guardarProductos() {
    this.router.navigate(['guardar-productos']);
  }

  guardarClientes() {
    this.router.navigate(['guardar-clientes']);
  }

  guardarEmpleados() {
    this.router.navigate(['guardar-empleados']);
  }

  guardarDepartamentos() {
    this.router.navigate(['guardar-departamentos']);
  }

  guardarProveedores() {
    this.router.navigate(['guardar-proveedores']);
  }

  //EDITAR--------------------------------------------------------------------------------------
  editarTiendas() {
    this.router.navigate(['editar-tiendas']);
  }

  editarProductos() {
    this.router.navigate(['editar-productos']);
  }
  
  editarClientes() {
    this.router.navigate(['editar-clientes']);
  }

  editarEmpleados() {
    this.router.navigate(['editar-empleados']);
  }

  editarDepartamentos() {
    this.router.navigate(['editar-departamentos']);
  }

  editarProveedores() {
    this.router.navigate(['editar-proveedores']);
  }

  //DETALLES-----------------------------------------------------------------------------------

  //Detalles de Tiendas
  detallesTiendas() {
    this.router.navigate(['detalles-tiendas']);
  }

  //Detalles de Productos por Departamento
  detallesDepartamentosProd() {
    this.router.navigate(['detalles-depaProduc']);
  }

  //detalles de Empleados por Departamento
  detallesDepartamentosEmpl() {
    this.router.navigate(['detalles-depaEmple']);
  }

}

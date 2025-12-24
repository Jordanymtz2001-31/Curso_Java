import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Cliente } from '../../../Entidad/cliente';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listas-c',
  imports: [FormsModule],
  templateUrl: './listas-c.html',
  styleUrl: './listas-c.css',
})
export class ListasC implements OnInit{

  //Set para controlar múltiples menús abiertos simultáneamente
  //Puede contener varios valores de tipo string que representan los menús abiertos
  protected openMenus = new Set<string>();

  //Creamos nuestro contructor y le inyectamos 
  constructor(private router: Router, private servicio:Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
  clientes!: Cliente[]; //Creamos una listar para almacenar
  cliente = new Cliente(); //Inicializamos un objeto para usarlo


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


  //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
  ngOnInit(): void{
    this.servicio.listarCliente().subscribe({
      next: (data) => { //data es la respuesta del servidor
        this.clientes = data; //Asignamos los datos a la variable clientes
      },
      error: (err) => { console.error('Error al cargar los clientes', err);
      }
    })
  }

  //Metodo para editar 
  btnEditar(id: number){
    this.router.navigate(['editar/cliente', id]);
  }

  //Metodo para eliminar 
  btnEliminar(id: number){
    Swal.fire({
      title: '¿Estás seguro de eliminar este cliente?',
      text: "¡Esta acción no se puede deshacer!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminarla'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.eliminarCliente(id).subscribe({
          next: (response) => {
            const mensaje = response.body?.message || response.body || 'El Cliente ha sido eliminada correctamente';
            Swal.fire(
              'Eliminada',
              mensaje,
              'success'
            );
            this.ngOnInit(); //Recargamos la lista de cliente despues de eliminar
          },
          error: (err) => {
            const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar el cliente';
            Swal.fire(
              'Error',
              errorMsg,
              'error'
            );
          }
        });
      }else if (result.isDismissed) {
        Swal.fire({
          title: 'Cancelado',
          icon : 'info',
          text: 'El Cliente no ha sido eliminado',
          showConfirmButton: false,
          timer: 1500
        });
      }
    });
  }

  //Metodo para Listar Mascotas dependiendo del Cliente
  btnListMascota(id: number){
    this.router.navigate(['detalle/cliente', id]);
  }

}

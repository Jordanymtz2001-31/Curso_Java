import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Mascota } from '../../../Entidad/mascota';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lista-m',
  imports: [FormsModule],
  templateUrl: './lista-m.html',
  styleUrl: './lista-m.css',
})
export class ListaM implements OnInit{

  //Set para controlar múltiples menús abiertos simultáneamente
  //Puede contener varios valores de tipo string que representan los menús abiertos
  protected openMenus = new Set<string>();

  //Creamos nuestro contructor y le inyectamos 
  constructor(private router: Router, private servicio:Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
  mascotas!: Mascota[]; //Creamos una listar para almacenar
  mascota = new Mascota(); //Inicializamos un objeto departamento para usarlo


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
    this.servicio.listarMascota().subscribe({
      next: (data) => { //data es la respuesta del servidor
        this.mascotas = data; //Asignamos los datos a la variable mascotas
      },
      error: (err) => { console.error('Error al cargar los mascotas', err);
      }
    })
  }

  //Metodo para editar 
  btnEditar(id: number){
    this.router.navigate(['editar/mascota', id]);
  }

  //Metodo para eliminar 
  btnEliminar(id: number){
    Swal.fire({
      title: '¿Estás seguro de eliminar esta mascota?',
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
            const mensaje = response.body?.message || response.body || 'La mascota ha sido eliminada correctamente';
            Swal.fire(
              'Eliminada',
              mensaje,
              'success'
            );
            this.ngOnInit(); //Recargamos la lista de cliente despues de eliminar
          },
          error: (err) => {
            const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar la mascota';
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
          text: 'La mascota no ha sido eliminado',
          showConfirmButton: false,
          timer: 1500
        });
      }
    });
  }
}

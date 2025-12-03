import { AfterContentInit, AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Servidor } from '../../Servidor/servidor';
import { Computadora } from '../../Entidad/computadora';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar',
  imports: [FormsModule, CommonModule],
  templateUrl: './listar.html',
  styleUrl: './listar.css',
})

//OnInit es un ciclo de vida que se ejecuta cuando el componente se inicializa
export class Listar implements OnInit {

  //Creamos el constructor para inicializar el componente
  constructor(private router: Router, private servicio: Servidor) {}

  //creamos una variable para almacenar la lista de computadoras
  listaComputadoras!: Computadora[] ; //Variable para almacenar la lista de computadoras
  computadora = new Computadora(); //Variable para almacenar una computadora individual
  
  //Método que se ejecuta cuando el componente se inicializa
  ngOnInit(): void {
    this.listarComputadoras();
  }

  //Método para listar las computadoras
  listarComputadoras() {
    //Llamamos al servicio para obtener la lista de computadoras
    //Subscribimos para manejar el flujo de datos que recibira al consumir el servicio
    this.servicio.listar().subscribe((data) => {
      this.listaComputadoras = data;
    });
  }

  //Metodo para eliminar una computadora
  eliminarComputadora(id: number) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¡No podrás revertir esta acción!",
      icon: 'warning',
      showCancelButton: true, //Mostrar botón de cancelar
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) { //
        this.servicio.eliminar(id).subscribe({
          next: () => {
            // Actualizamos la lista localmente sin hacer nueva petición al servidor
            this.listarComputadoras();            
            Swal.fire(
              '¡Eliminado!',
              'La computadora ha sido eliminada.',
              'success'
            );
          },
          error: (err) => {
            Swal.fire(
              'Error',
              'No se pudo eliminar la computadora',
              'error'
            );
            console.error('Error al eliminar la computadora', err);
          }
        });
      }
    });
  }

  

}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Servidor } from '../../../Servidor/servidor';
import { Router, RouterModule } from '@angular/router';
import { Padre } from '../../../Entidad/padre';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-padre',
  imports: [CommonModule],
  templateUrl: './listar-padre.html',
  styleUrl: './listar-padre.css',
})
//OnInit es un ciclo de vida de Angular que se ejecuta al iniciar el componente 
export class ListarPadre implements OnInit {

  //Contructor para inicializar el componente y le inyectamos el servicio Servidor
  //ChangDetectorRef para detectar cambios en la vista
  constructor(private router: Router, private servidor : Servidor, private cdr: ChangeDetectorRef) {}

  //Instanciamos la clase de Padre
  padre: Padre = new Padre();
  padres: Padre[] = []; //Arreglo para almacenar los padres

  ngOnInit(): void {

    this.listarPadres(); //Llamamos al metodo para listar los padres al iniciar el componente

  }

  //Metodo para listar los padres
  listarPadres() {
    this.servidor.listarPadres().subscribe({
      next: (data) => {
        this.padres = data; //Asignamos los datos obtenidos del servidor al arreglo de padres
        console.log('Padres cargados:', this.padres);
        this.cdr.detectChanges(); // Fuerza la detección de cambios
      },
      error: (err) => {
        console.error('Error al cargar padres:', err);
      }
    });
  }

    //Metodo de editar
  editarPadre(padre: Padre) {
    //Colocamos localStorage para almacenar temporalmente el objeto padre
    localStorage.setItem("padre", JSON.stringify(padre));
    this.router.navigate(['editarPadre']); //Navegamos al componente de editar padre
  }

  //Metodo para eliminar un padre
  eliminarPadre(idPadre: number) {
    Swal.fire({
      title: '¿Estás seguro de Eliminar?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: 'rgba(51, 51, 221, 1)',
      cancelButtonColor: 'rgba(221, 51, 51, 1)',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servidor.eliminarPadre(idPadre).subscribe(data => {
          Swal.fire({
            title: 'Eliminado',
            icon: 'success',
            text: JSON.stringify(data),
            showConfirmButton: false,
            timer: 1500
          });
          this.listarPadres(); //Refrescamos la lista de padres después de eliminar
        });
      }else if (result.isDismissed) {
        Swal.fire({
          title: 'Cancelado',
          icon: 'info',
          text: 'La eliminación ha sido cancelada.',
          showConfirmButton: false, //No mostrar el botón de confirmar
          timer: 1500
        });
      }
    });
  }
}
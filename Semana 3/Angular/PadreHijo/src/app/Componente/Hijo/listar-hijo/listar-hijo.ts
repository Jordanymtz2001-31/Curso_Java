import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Hijo } from '../../../Entidad/hijo';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-hijo',
  imports: [CommonModule],
  templateUrl: './listar-hijo.html',
  styleUrl: './listar-hijo.css',
})
export class ListarHijo implements OnInit{

  //Constructor para inicializar el componente
  constructor(private servidor: Servidor, private router: Router, private detector: ChangeDetectorRef) {}

  //Instanciamos la clase de Hijo
  hijo: Hijo = new Hijo();
  hijos: Hijo[] = []  //Arreglo para almacenar los hijos - inicializado como array vacío
  loading: boolean = false;

  //Metodo que se ejecuta al iniciar el componente
  ngOnInit(): void {
    this.botonListaHijos(); //Llamamos al metodo para listar los hijos al iniciar el componente
  }

  botonListaHijos() {
    this.loading = true;
    this.servidor.listarHijos().subscribe({
      next: (data) => {
        this.hijos = data; //Asignamos los datos obtenidos del servidor al arreglo de hijos
        this.loading = false;
        this.detector.detectChanges(); // Fuerza la detección de cambios
        console.log(JSON.stringify(this.hijos));
      },
      error: (err) => {
        console.error('Error al cargar hijos:', err);
        this.loading = false;
    }
    });
  }

  //Metodo de editar un hijo
  botonEditarHijo(hijo: Hijo) {
    //Colocamos localStorage para almacenar temporalmente el objeto hijo
    localStorage.setItem("hijo", JSON.stringify(hijo));
    this.router.navigate(['editarHijo']); //Navegamos al componente de editar hijo
  }

  //Metodo para eliminar un hijo
  botonEliminarHijo(idHijo: number) {
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
        this.servidor.eliminarHijo(idHijo).subscribe({
          next: (data) => {
            Swal.fire({
              title: 'Eliminado',
              icon: 'success',
              text: JSON.stringify(data),
              showConfirmButton: false,
              timer: 1500
            });
            this.botonListaHijos(); //Refrescamos la lista de hijos después de eliminar
          },
          error: (err) => {
            Swal.fire({
              title: 'Error',
              icon: 'error',
              text: 'No se pudo eliminar el hijo.',
              showConfirmButton: true
            });
            console.error('Error al eliminar hijo:', err);
          }
        });
      }else if (result.isDismissed) {
        Swal.fire({
          title: 'Cancelado',
          icon: 'info',
          text: 'La acción ha sido cancelada.',
          showConfirmButton: false,
          timer: 1500
        });
      }
    })
  }

}

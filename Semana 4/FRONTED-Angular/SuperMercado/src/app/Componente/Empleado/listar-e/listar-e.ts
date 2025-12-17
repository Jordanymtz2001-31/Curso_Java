import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Empleado } from '../../../Entidad/empleado';
import { Tienda } from '../../../Entidad/tienda';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-e',
  imports: [FormsModule],
  templateUrl: './listar-e.html',
  styleUrl: './listar-e.css',
})
export class ListarE implements OnInit{

  //Creamos nuestro contructor y le inyectamos 
  constructor(private router: Router, private servicio:Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
  empleados!: Empleado[];
  empleado = new Empleado(); //Inicializamos un objeto tienda para usarlor

  //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
  ngOnInit(): void{
    this.servicio.listarEmpleados().subscribe({
      next: (data) => { //data es la respuesta del servidor
        this.empleados = data; //Asignamos los datos a la variable empleados
      },
      error: (err) => { console.error('Error al cargar los empleados', err);
      }
    })
  }

  //Metodo para editar una tienda
  btnEditar(id: number){
    this.router.navigate(['editar-empleados', id]);
  }

  //Metodo para eliminar una tienda
  btnEliminar(id: number){
    Swal.fire({
      title: '¿Estás seguro de eliminar este empleado?',
      text: "¡Esta acción no se puede deshacer!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminarla'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.eliminarEmpleados(id).subscribe({
          next: (response) => {
            const mensaje = response.body?.message || response.body || 'El empleado ha sido eliminada correctamente';
            Swal.fire(
              'Eliminada',
              mensaje,
              'success'
            );
            this.ngOnInit(); //Recargamos la lista de empleados despues de eliminar
          },
          error: (err) => {
            const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar el empleado';
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
          text: 'El empleado no ha sido eliminado',
          showConfirmButton: false,
          timer: 1500
        });
      }
    });
  }
}

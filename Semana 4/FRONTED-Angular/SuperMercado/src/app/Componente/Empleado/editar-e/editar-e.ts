import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Empleado } from '../../../Entidad/empleado';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-e',
  imports: [FormsModule],
  templateUrl: './editar-e.html',
  styleUrl: './editar-e.css',
})
export class EditarE implements OnInit{

  // Creamos el constructor que inyecta el servicio Servidor
  constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }

  // Creamos la instancia de la entidad Clientes
  empleado: Empleado = new Empleado(); //Intanciamos con Empleado
  loading = true; //Variable para indicar que si esta cargando
  error = '';

  ngOnInit(): void {
    
    //Obtenerl El iD de la tienda desde la ruta
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarEmpelado(id);
      } else {
        this.error = 'ID de empleado no válido';
        this.loading = false;
      }
    })
  }

  cargarEmpelado(id: number): void {
    this.servicio.buscarEmpleadoId(id).subscribe({
      next: (Response) => {
        Swal.fire({
          title: 'Cargado...✓'
        });
        this.empleado = Response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar el empleado:', err);
        this.error = 'Error al cargar los datos del empleado';
        this.loading = false;
      }
    });
  }

  btnEditarEmpleado(): void {
    Swal.fire({
      title: "¿Estás seguro de editar este empleado?",  // ← Corregido texto
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.editarEmpleados(this.empleado).subscribe({
          next: (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Empleado editado',  // ← Corregido typo
              text: response?.message || 'Empleado editado correctamente',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['listar-empleados']);  // ← DENTRO del success
            });
          },
          error: (err: any) => {  // ← CORREGIDO: fuera de then()
            Swal.fire({
              icon: 'error',
              title: 'Error al editar el empleado',  
              text: err.error?.error || 'Error desconocido',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
}

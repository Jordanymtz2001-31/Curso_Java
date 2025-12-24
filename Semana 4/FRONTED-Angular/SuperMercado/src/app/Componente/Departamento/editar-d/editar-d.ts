import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Departamento } from '../../../Entidad/departamento';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-d',
  imports: [FormsModule],
  templateUrl: './editar-d.html',
  styleUrl: './editar-d.css',
})
export class EditarD implements OnInit{
  // Creamos el constructor que inyecta el servicio Servidor
  constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }

  // Creamos la instancia de la entidad 
  departamento: Departamento = new Departamento(); //Intanciamos con Departamentos
  loading = true; //Variable para indicar que si esta cargando
  error = '';

  ngOnInit(): void {
    
    //Obtenerl El iD del Departamentos desde la ruta
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarDepartamento(id);
      } else {
        this.error = 'ID de cliente no válido';
        this.loading = false;
      }
    })
  }

  cargarDepartamento(id: number): void {
    this.servicio.buscarDepartamentoId(id).subscribe({
      next: (Response) => {
        Swal.fire({
          title: 'Cargado...✓'
        });
        this.departamento = Response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar la tienda:', err);
        this.error = 'Error al cargar los datos del departamento';
        this.loading = false;
      }
    });
  }

  btnEditarDepartamento(): void {
    Swal.fire({
      title: "¿Estás seguro de editar este departamento?",  // ← Corregido texto
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.editarDepartamentos(this.departamento).subscribe({
          next: (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Departamento editado',  // ← Corregido typo
              text: response?.message || 'Deparatamento editado correctamente',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['listar-departamentos']);  // ← DENTRO del success
            });
          },
          error: (err: any) => {  // ← CORREGIDO: fuera de then()
            Swal.fire({
              icon: 'error',
              title: 'Error al editar departamento',  
              text: err.error?.error || 'Error desconocido',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
}

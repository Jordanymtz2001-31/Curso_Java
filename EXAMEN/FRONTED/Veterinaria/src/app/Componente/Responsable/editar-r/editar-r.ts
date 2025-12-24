import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Responsable } from '../../../Entidad/responsable';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-r',
  imports: [FormsModule],
  templateUrl: './editar-r.html',
  styleUrl: './editar-r.css',
})
export class EditarR implements OnInit{

  // Creamos el constructor que inyecta el servicio Servidor
  constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }

  // Creamos la instancia de la entidad Responsables
  responsable: Responsable = new Responsable(); //Intanciamos con Responsable
  loading = true; //Variable para indicar que si esta cargando
  error = '';

  ngOnInit(): void {
    
    //Obtenerl El iD del responsable desde la ruta para cargar los datos
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarResponsable(id);
      } else {
        this.error = 'ID del Responsable no válido';
        this.loading = false;
      }
    })
  }

  cargarResponsable(id: number): void {
    this.servicio.buscarResponsableID(id).subscribe({
      next: (Response) => {
        Swal.fire({
          title: 'Cargado...✓'
        });
        this.responsable = Response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar el Responsable:', err);
        this.error = 'Error al cargar los datos del Responsable';
        this.loading = false;
      }
    });
  }

  btnEditarResponsable(): void {
    Swal.fire({
      title: "¿Estás seguro de editar este Responsable?",  
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.editarResponsable(this.responsable).subscribe({
          next: (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Responsable editado', 
              text: response?.message || 'Responsable editado correctamente',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['listar/responsables']); 
            });
          },
          error: (err: any) => {  
            Swal.fire({
              icon: 'error',
              title: 'Error al editar Responsable',  
              text: err.error?.error || 'Error desconocido',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
}

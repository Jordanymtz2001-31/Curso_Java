import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Mascota } from '../../../Entidad/mascota';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-m',
  imports: [FormsModule],
  templateUrl: './editar-m.html',
  styleUrl: './editar-m.css',
})
export class EditarM implements OnInit{

  // Creamos el constructor que inyecta el servicio Servidor
  constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }

  // Creamos la instancia de la entidad Mascotas
  mascota: Mascota = new Mascota(); //Intanciamos con Mascota
  loading = true; //Variable para indicar que si esta cargando
  error = '';

  ngOnInit(): void {
    
    //Obtenerl El iD de la mascota desde la ruta para cargar los datos
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarMascota(id);
      } else {
        this.error = 'ID de la mascota no válido';
        this.loading = false;
      }
    })
  }

  cargarMascota(id: number): void {
    this.servicio.buscarMascotaID(id).subscribe({
      next: (Response) => {
        Swal.fire({
          title: 'Cargado...✓'
        });
        this.mascota = Response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar la Mascota:', err);
        this.error = 'Error al cargar los datos de las Mascota';
        this.loading = false;
      }
    });
  }

  btnEditarMascota(): void {
    Swal.fire({
      title: "¿Estás seguro de editar esta Mascota?",  
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.editarMascota(this.mascota).subscribe({
          next: (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Mascota editado', 
              text: response?.message || 'Mascota editado correctamente',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['listar/mascotas']); 
            });
          },
          error: (err: any) => {  
            Swal.fire({
              icon: 'error',
              title: 'Error al editar mascota',  
              text: err.error?.error || 'Error desconocido',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Tienda } from '../../../Entidad/tienda';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-t',
  imports: [FormsModule],
  templateUrl: './editar-t.html',
  styleUrl: './editar-t.css',
})
export class EditarT implements OnInit{

  // Creamos el constructor que inyecta el servicio Servidor
  constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }

  
  tienda: Tienda = new Tienda(); //Intanciamos con Tienda
  loading = true; //Variable para indicar que si esta cargando
  error = '';

  ngOnInit(): void {
    
    //Obtenerl El iD de la tienda desde la ruta
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarTienda(id);
      } else {
        this.error = 'ID de la tienda no válido';
        this.loading = false;
      }
    })
  }

  cargarTienda(id: number): void {
    this.servicio.buscarTiendaID(id).subscribe({
      next: (Response) => {
        Swal.fire({
          title: 'Cargado...✓'
        });
        this.tienda = Response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar la tienda:', err);
        this.error = 'Error al cargar los datos de la tienda';
        this.loading = false;
      }
    });
  }

  btnEditarTiendas(): void {
    Swal.fire({
      title: "¿Estás seguro de editar esta tienda?",  // ← Corregido texto
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.editarTiendas(this.tienda).subscribe({
          next: (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Tienda editada',  // ← Corregido typo
              text: response?.message || 'Tienda editada correctamente',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['listar-tiendas']);  // ← DENTRO del success
            });
          },
          error: (err: any) => {  // ← CORREGIDO: fuera de then()
            Swal.fire({
              icon: 'error',
              title: 'Error al editar la tienda',  
              text: err.error?.error || 'Error desconocido',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }

}

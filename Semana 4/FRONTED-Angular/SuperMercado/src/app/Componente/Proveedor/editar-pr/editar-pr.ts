import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Proveedor } from '../../../Entidad/proveedor';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-pr',
  imports: [FormsModule],
  templateUrl: './editar-pr.html',
  styleUrl: './editar-pr.css',
})
export class EditarPR implements OnInit{

  // Creamos el constructor que inyecta el servicio Servidor
    constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }
  
    // Creamos la instancia de la entidad Clientes
    proveedor: Proveedor = new Proveedor(); //Intanciamos con Tienda
    loading = true; //Variable para indicar que si esta cargando
    error = '';
  
    ngOnInit(): void {
      
      //Obtenerl El iD de la tienda desde la ruta
      this.route.params.subscribe(params =>{
        const id = +params['id']; // El + convierte string a number
        if (id) {
          this.cargarProveedor(id);
        } else {
          this.error = 'ID de proveedor no válido';
          this.loading = false;
        }
      })
    }
  
    cargarProveedor(id: number): void {
      this.servicio.buscarProveedorId(id).subscribe({
        next: (Response) => {
          Swal.fire({
            title: 'Cargado...✓'
          });
          this.proveedor = Response;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al cargar el proveedor:', err);
          this.error = 'Error al cargar los datos del proveedor';
          this.loading = false;
        }
      });
    }
  
    btnEditarProveedor(): void {
      Swal.fire({
        title: "¿Estás seguro de editar el proveedor?",  // ← Corregido texto
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, editar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.servicio.editarProveedores(this.proveedor).subscribe({
            next: (response: any) => {
              Swal.fire({
                icon: 'success',
                title: 'Proveedor editada',  // ← Corregido typo
                text: response?.message || 'Proveedor editada correctamente',
                showConfirmButton: false,
                timer: 1500
              }).then(() => {
                this.router.navigate(['listar-proveedores']);  // ← DENTRO del success
              });
            },
            error: (err: any) => {  // ← CORREGIDO: fuera de then()
              Swal.fire({
                icon: 'error',
                title: 'Error al editar el proveedor',  
                text: err.error?.error || 'Error desconocido',
                confirmButtonText: 'Aceptar'
              });
            }
          });
        }
      });
    }

}

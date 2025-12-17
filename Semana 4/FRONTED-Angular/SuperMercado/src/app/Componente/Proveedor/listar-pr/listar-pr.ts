import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Proveedores } from '../../../Entidad/proveedores';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-pr',
  imports: [FormsModule],
  templateUrl: './listar-pr.html',
  styleUrl: './listar-pr.css',
})
export class ListarPR implements OnInit {

  //Creamos el constructor y le inyectamos los servicios necesarios
  constructor(private router: Router, private servicio: Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
    proveedores!: Proveedores[];
    proveedor = new Proveedores(); //Inicializamos un objeto tienda para usarlor
  
    //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
    ngOnInit(): void{
      this.servicio.listarProveedores().subscribe({
        next: (data) => { //data es la respuesta del servidor
          this.proveedores = data; //Asignamos los datos a la variable tiendas
        },
        error: (err) => { 
          console.error('Error al cargar las proveedores', err);
        }
      })
    }
  
    //Metodo para editar una tienda
    btnEditar(id: number){
      this.router.navigate(['editar-proveedores', id]);
    }
  
    //Metodo para eliminar una tienda
    btnEliminar(id: number){
      Swal.fire({
        title: '¿Estás seguro de eliminar este proveedor?',
        text: "¡Esta acción no se puede deshacer!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminarlo'
      }).then((result) => {
        if (result.isConfirmed) {
          this.servicio.eliminarProveedores(id).subscribe({
            next: (response) => {
                // ← EXTRAE el STRING del objeto
              const mensaje = response.body?.message || response.body || 'El proveedor ha sido eliminada correctamente';
              Swal.fire(
                'Eliminada',
                mensaje,  
                'success'
              );
              this.ngOnInit(); 
            },
            error: (err) => {
              const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar el Proveedor';
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
            text: 'El proveedor no ha sido eliminado  ',
            showConfirmButton: false,
            timer: 1500
          });
        }
      });
    }

}

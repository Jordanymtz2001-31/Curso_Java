import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { Router } from '@angular/router';
import { Producto } from '../../../Entidad/producto';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-p',
  imports: [FormsModule],
  templateUrl: './listar-p.html',
  styleUrl: './listar-p.css',
})
export class ListarP implements OnInit {

  //Creamos nuestro contructor y le inyectamos
  constructor(private router: Router, private servicio : Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
    productos!: Producto[];
    producto = new Producto(); //Inicializamos un objeto producto para usarlor
  
    //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
    ngOnInit(): void{
      this.servicio.listarProductos().subscribe({
        next: (data) => { //data es la respuesta del servidor
          this.productos = data; //Asignamos los datos a la variable productos
        },
        error: (err) => { console.error('Error al cargar los productos', err);
        }
      })
    }
  
    //Metodo para editar un producto
    btnEditar(id: number){
      this.router.navigate(['editar-productos', id]);
    }
  
    //Metodo para eliminar un producto
    btnEliminar(id: number){
      Swal.fire({
        title: '¿Estás seguro de eliminar este producto?',
        text: "¡Esta acción no se puede deshacer!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminarla'
      }).then((result) => {
        if (result.isConfirmed) {
          this.servicio.eliminarProductos(id).subscribe({
            next: (response) => {
              const mensaje = response.body?.message || response.body || 'El producto ha sido eliminada correctamente';
              Swal.fire(
                'Eliminada',
                mensaje,
                'success'
              );
              this.ngOnInit(); //Recargamos la lista de productos despues de eliminar
            },
            error: (err) => {
              const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar el producto';
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
            text: 'El producto no ha sido eliminado',
            showConfirmButton: false,
            timer: 1500
          });
        }
      });
    }
  
}

import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Cliente } from '../../../Entidad/cliente';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-c',
  imports: [FormsModule],
  templateUrl: './listar-c.html',
  styleUrl: './listar-c.css',
})
export class ListarC implements OnInit {
    //Creamos nuestro contructor y le inyectamos 
  constructor(private router: Router, private servicio:Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
  clientes!: Cliente[];
  cliente = new Cliente(); //Inicializamos un objeto tienda para usarlor

  //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
  ngOnInit(): void{
    this.servicio.listarClientes().subscribe({
      next: (data) => { //data es la respuesta del servidor
        this.clientes = data; //Asignamos los datos a la variable tiendas
      },
      error: (err) => { console.error('Error al cargar los clientes', err);
      }
    })
  }

  //Metodo para editar una tienda
  btnEditar(id: number){
    this.router.navigate(['editar-clientes', id]);
  }

  //Metodo para eliminar una tienda
  btnEliminar(id: number){
    Swal.fire({
      title: '¿Estás seguro de eliminar este cliente?',
      text: "¡Esta acción no se puede deshacer!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminarla'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.eliminarClientes(id).subscribe({
          next: (response) => {
            const mensaje = response.body?.message || response.body || 'El cliente ha sido eliminada correctamente';
            Swal.fire(
              'Eliminada',
              mensaje,
              'success'
            );
            this.ngOnInit(); //Recargamos la lista de clientes despues de eliminar
          },
          error: (err) => {
            const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar el cliente';
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
          text: 'El cliente no ha sido eliminado',
          showConfirmButton: false,
          timer: 1500
        });
      }
    });
  }  
}

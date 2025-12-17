import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { Router } from '@angular/router';
import { Tienda } from '../../../Entidad/tienda';
import Swal from 'sweetalert2';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-listar-t',
  imports: [FormsModule],
  templateUrl: './listar-t.html',
  styleUrl: './listar-t.css',
})
//OninInit es un ciclo de vida del componente que se ejecuta al inicializar 
export class ListarT implements OnInit {

  //Creamos nuestro contructor y le inyectamos 
  constructor(private router: Router, private servicio:Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
  tiendas!: Tienda[];
  tienda = new Tienda(); //Inicializamos un objeto tienda para usarlor

  //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
  ngOnInit(): void{
    this.servicio.listarTiendas().subscribe({
      next: (data) => { //data es la respuesta del servidor
        this.tiendas = data; //Asignamos los datos a la variable tiendas
      },
      error: (err) => { 
        console.error('Error al cargar las tiendas', err);
      }
    })
  }

  //Metodo para editar una tienda
  btnEditar(id: number){
    this.router.navigate(['editar-tiendas', id]);
  }

  //Metodo para eliminar una tienda
  btnEliminar(id: number){
  Swal.fire({
    title: '¿Estás seguro de eliminar esta tienda?',
    text: "¡Esta acción no se puede deshacer!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sí, eliminarla'
  }).then((result) => {
    if (result.isConfirmed) {
      this.servicio.eliminarTiendas(id).subscribe({
        next: (response) => {
          // ← EXTRAE el STRING del objeto
          const mensaje = response.body?.message || response.body || 'La tienda ha sido eliminada correctamente';
          Swal.fire(
            'Eliminada',
            mensaje,  
            'success'
          );
          this.ngOnInit(); 
        },
        error: (err) => {
          const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar la tienda';
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
      text: 'La tienda no ha sido eliminado  ',
      showConfirmButton: false,
      timer: 1500
      });
    }
  });
}
}

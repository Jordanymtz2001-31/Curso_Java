import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Producto } from '../../../Entidad/producto';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar-p',
  imports: [FormsModule],
  templateUrl: './guardar-p.html',
  styleUrl: './guardar-p.css',
})
export class GuardarP implements OnInit{

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router: Router, private service: Servidor) { }

  Producto: Producto = new Producto() //Intancia de Producto
  Productos: Producto[] = [] //Creamos una lista para almacenar los productos

  //Metodo de OnInit
  ngOnInit(): void {
      
  }
  
  //Boton de Guardar
  btnGuardardProducto(): void{
    this.service.guardarProductos(this.Producto).subscribe({
      next: (response: any) =>{
      Swal.fire({
        title: 'Guardado',
        text: response.body.message || 'Productos guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }),
        this.router.navigate(['listar-productos']); //Navegamos a la lista de productos despues de guardar
      },
      error: (err) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: err.error?.error || 'Error desconocido', //Mostramos el error del backend
          confirmButtonText: 'Aceptar'
        });
      }
    });
  }

}

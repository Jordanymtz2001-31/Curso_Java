import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Tienda } from '../../../Entidad/tienda';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-guardar-t',
  imports: [FormsModule, CommonModule],
  templateUrl: './guardar-t.html',
  styleUrl: './guardar-t.css',
})
export class GuardarT implements OnInit{

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router: Router, private service: Servidor) { }

  Tienda: Tienda = new Tienda() //Intancia de Tienda
  Tiendas: Tienda[] = [] //Creamos una lista para almacenar las tiendas


  //Metodo de OnInit
  ngOnInit(): void {
      
  }
  
  //Boton de Guardar
  btnGuardardTiendas(): void{
    this.service.guardarTiendas(this.Tienda).subscribe({
      next: (response: any) =>{ //Colocamos any para capturar todo tipo de respuestas
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Tienda guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }),
        this.router.navigate(['listar-tiendas']); //Navegamos a la lista de tiendas despues de guardar
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

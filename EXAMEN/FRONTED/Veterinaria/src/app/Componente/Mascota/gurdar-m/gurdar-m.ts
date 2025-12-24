import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Mascota } from '../../../Entidad/mascota';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-gurdar-m',
  imports: [FormsModule],
  templateUrl: './gurdar-m.html',
  styleUrl: './gurdar-m.css',
})
export class GurdarM implements OnInit{

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router:Router, private service: Servidor) {}

  mascota: Mascota = new Mascota() //Intancia de Mascota

  //Metodo de OnInit
  ngOnInit(): void {

  }
  
  //Boton de Guardar
  btnGuardardMascota(): void{
    this.service.guardarMascota(this.mascota).subscribe({
      next: (response: any) =>{
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Mascota guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }).then(() =>{
        this.router.navigate(['listar/mascotas']); //Navegamos a la lista de mascotas despues de guardar
      });
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

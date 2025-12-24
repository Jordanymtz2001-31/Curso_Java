import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Responsable } from '../../../Entidad/responsable';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar-r',
  imports: [FormsModule],
  templateUrl: './guardar-r.html',
  styleUrl: './guardar-r.css',
})
export class GuardarR implements OnInit{

    //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router:Router, private service: Servidor) {}

  responsable: Responsable = new Responsable() //Intancia de Responsable

  //Metodo de OnInit
  ngOnInit(): void {

  }
  
  //Boton de Guardar
  btnGuardardResponsable(): void{
    this.service.guardarResponsable(this.responsable).subscribe({
      next: (response: any) =>{
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Responsable guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }).then(() =>{
        this.router.navigate(['listar/responsables']); //Navegamos a la lista de responsables despues de guardar
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

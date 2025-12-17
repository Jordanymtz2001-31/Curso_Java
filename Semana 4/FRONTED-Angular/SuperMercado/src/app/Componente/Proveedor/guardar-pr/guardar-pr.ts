import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Proveedores } from '../../../Entidad/proveedores';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar-pr',
  imports: [FormsModule],
  templateUrl: './guardar-pr.html',
  styleUrl: './guardar-pr.css',
})
export class GuardarPR implements OnInit{

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router: Router, private service: Servidor) { }

  Proveedor: Proveedores = new Proveedores() //Intancia de Proveedor
  Proveedores: Proveedores[] = [] //Creamos una lista para almacenar los proveedores

  //Metodo de OnInit
  ngOnInit(): void {
      
  }
  
  //Boton de Guardar
  btnGuardardProveedores(): void{
    this.service.guardarProveedores(this.Proveedor).subscribe({
      next: (response: any) =>{ //Colocamos any para capturar todo tipo de respuestas
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Proveedor guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }),
        this.router.navigate(['listar-proveedores']); //Navegamos a la lista de tiendas despues de guardar
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

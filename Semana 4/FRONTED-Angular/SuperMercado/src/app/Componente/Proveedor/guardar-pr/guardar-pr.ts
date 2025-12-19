import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import Swal from 'sweetalert2';
import { Proveedor } from '../../../Entidad/proveedor';

@Component({
  selector: 'app-guardar-pr',
  imports: [FormsModule],
  templateUrl: './guardar-pr.html',
  styleUrl: './guardar-pr.css',
})
export class GuardarPR implements OnInit{

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router: Router, private service: Servidor) { }

  Proveedor: Proveedor = new Proveedor() //Intancia de Proveedor
  Proveedores: Proveedor[] = [] //Creamos una lista para almacenar los proveedores

  //Metodo de OnInit
  ngOnInit(): void {
      
  }
  
  //Boton de Guardar
  btnGuardardProveedores(): void{
    console.log('JSON ENVIADO:', JSON.stringify(Proveedor, null, 2));
    this.service.guardarProveedores(this.Proveedor).subscribe({
      next: (response: any) =>{ //Colocamos any para capturar todo tipo de respuestas
        console.log('ÉXITO:', response.body);
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

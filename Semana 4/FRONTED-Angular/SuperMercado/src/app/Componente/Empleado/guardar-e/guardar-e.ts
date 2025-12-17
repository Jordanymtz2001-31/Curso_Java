import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Empleado } from '../../../Entidad/empleado';
import { Departamento } from '../../../Entidad/departamento';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar-e',
  imports: [FormsModule],
  templateUrl: './guardar-e.html',
  styleUrl: './guardar-e.css',
})
export class GuardarE implements OnInit {

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router: Router, private service: Servidor) { }

  Empleado: Empleado = new Empleado() //Intancia de Empleado
  Empleados: Empleado[] = [] //Creamos una lista para almacenar los empleados

  //Metodo de OnInit
  ngOnInit(): void {
      
  }
  
  //Boton de Guardar
  btnGuardardEmpleado(): void{
    this.service.guardarEmpleados(this.Empleado).subscribe({
      next: (response: any) =>{
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Empleado guardado con éxito',
        icon: 'success',
        showConfirmButton: false
      }),
        this.router.navigate(['listar-empleados']); //Navegamos a la lista de empleados despues de guardar
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

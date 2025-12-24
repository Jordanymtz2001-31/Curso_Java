import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Departamento } from '../../../Entidad/departamento';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar-d',
  imports: [FormsModule],
  templateUrl: './guardar-d.html',
  styleUrl: './guardar-d.css',
})
export class GuardarD implements OnInit{

  //Creamos un contructor para inicializar con sus inyecciones
  constructor (private router:Router, private service: Servidor) {}

  departamento: Departamento = new Departamento() //Intancia de Departamento

  //Metodo de OnInit
  ngOnInit(): void {

  }
  
  //Boton de Guardar
  btnGuardardDepartamento(): void{
    this.service.guardarDepartamentos(this.departamento).subscribe({
      next: (response: any) =>{
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Departamento guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }),
        this.router.navigate(['listar-departamentos']); //Navegamos a la lista de departamentos despues de guardar
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

import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Computadora } from '../../Entidad/computadora';
import { Router } from '@angular/router';
import { Servidor } from '../../Servidor/servidor';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar',
  imports: [FormsModule],
  templateUrl: './guardar.html',
  styleUrls: ['./guardar.css'],
})

//OnInit para inicializar el componente
export class Guardar implements OnInit {

  //Creamos el constructor e inyectamos el servicio de computadora
  constructor(private router: Router, private servicio: Servidor) {}

  //Creamos una instancia de la entidad Computadora
  compu : Computadora = new Computadora();

  //Método para inicializar el componente
  ngOnInit(): void {  
  }

  //Método para guardar la computadora
  guardarComputadora(): void {
    this.servicio.guardar(this.compu).subscribe({
      next: (data) => {
      //Ocupamos el mensaje de alerta para notificar al usuario
      Swal.fire({
        icon: 'success',
        title: 'Computadora guardada',
        text: JSON.stringify(data), //Mostramos el mensaje del backen
        showConfirmButton: false,
        timer: 1500
    });
    //Una ves guardada la computadora, navegamos al componente listar
      this.router.navigate(['/lista']);
    }, 
    error: (error) => { 
      Swal.fire({
        icon: 'error',
        title: 'Error al guardar la computadora',
        text: JSON.stringify(error), //Mostramos el mensaje del backen
        confirmButtonText : 'Aceptar'
      });
    }
    });
  }
}

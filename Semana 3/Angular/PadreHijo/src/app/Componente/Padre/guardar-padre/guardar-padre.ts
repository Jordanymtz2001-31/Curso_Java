import { Component, OnInit } from '@angular/core';
import { Servidor } from '../../../Servidor/servidor';
import { Router } from '@angular/router';
import { Padre } from '../../../Entidad/padre';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-guardar-padre',
  standalone: true, //Indica que este componente es independiente
  imports: [FormsModule],
  templateUrl: './guardar-padre.html',
  styleUrl: './guardar-padre.css',
})
//OnInit para inicializar el componente 
export class GuardarPadre implements OnInit {

  //Creamos el constructor e inyectamos el servicio de padre
  constructor(private servidor: Servidor, private router: Router) {}

  //Instancia de la entidad Padre
  padre: Padre = new Padre();

  //Método para inicializar el componente
  ngOnInit(): void {

  }

  //Método para guardar el padre
  guardarPadre(): void { //El data significa que recibira datos del backend
    this.servidor.guardarPadre(this.padre).subscribe(data =>{
      //y usamos Swal para mostrar una alerta de éxito
      Swal.fire({
        icon: 'success',
        title: 'Padre guardado',
        text: JSON.stringify(data), //Mostramos el mensaje del backen
        showConfirmButton: false,
        timer: 1500
      });
      this.router.navigate(['listaPadres']);
    });
  }
}

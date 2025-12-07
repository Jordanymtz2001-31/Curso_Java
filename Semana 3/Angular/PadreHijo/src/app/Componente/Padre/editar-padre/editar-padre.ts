import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Padre } from '../../../Entidad/padre';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-padre',
  imports: [FormsModule],
  templateUrl: './editar-padre.html',
  styleUrl: './editar-padre.css',
})
// OnInit es un ciclo de vida de Angular que se ejecuta al iniciar el componente
export class EditarPadre implements OnInit {

  //Constructor para inicializar el componente
  constructor(private router:Router, private servidor: Servidor) {}

  //Instanciamos la clase de Padre
  padre: Padre = new Padre();


  //Metodo que se ejecuta al iniciar el componente
  ngOnInit(): void {
    //Necesitamos que carge los datos del padre al iniciar el componente
    this.buscarPadre(); //Llamamos al metodo para buscar el padre al iniciar el componente
    }

    buscarPadre() {
      //Obtenemos los datos del padre almacenados en localStorage
      const padreData = localStorage.getItem("padre");
      if(padreData) { // si existen datos
        console.log("Datos del padre encontrados en localStorage:", padreData);
        this.padre = JSON.parse(padreData); // los parseamos a un objeto Padre
      }//Después llamamos al servicio para buscar el padre por su id
      this.servidor.buscarPadre(this.padre.idPadre).subscribe(data => {
        this.padre = data; //Asignamos los datos obtenidos del servidor al objeto padre
        Swal.fire({
          title: 'Editar',
          icon: 'success',
          text: 'Datos del padre cargados correctamente',
          showConfirmButton: false,
          timer: 1500
        })
      });
    }

    //Metodo para editar el padre
    editarPadre() {
      this.servidor.editarPadre(this.padre).subscribe(data => {
        Swal.fire({
          title: 'Editar',
          icon: 'success',
          text: JSON.stringify(data), //Mostramos la respuesta del servidor(Backend)
          showConfirmButton: false,
          timer: 1500
        });
        this.router.navigate(['listaPadres']); // Navegamos a la lista de padres después de editar
      });
    }
}
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Hijo } from '../../../Entidad/hijo';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-hijo',
  imports: [FormsModule],
  templateUrl: './editar-hijo.html',
  styleUrl: './editar-hijo.css',
})
export class EditarHijo implements OnInit{

  //Constructor para inicializar el componente
  constructor(private router: Router, private servidor: Servidor) {}

  //Hacemos instancia de la clase Hijo
  hijo: Hijo = new Hijo();

  //Metodo que se ejecuta al iniciar el componente
  ngOnInit(): void {
    //Necesitamos que carge los datos del hijo al iniciar el componente
    this.buscarHijo(); //Llamamos al metodo para buscar el hijo al iniciar el componente
  }

  buscarHijo() {
    //Obtenemos los datos del hijo almacenados en localStorage
    const hijoData = localStorage.getItem("hijo");
    //Si existen datos
    if(hijoData){
      console.log("Datos del hijo encontrados:", hijoData);
      this.hijo = JSON.parse(hijoData); //Los parseamos a un objeto Hijo
    }//Después llamamos al servicio para buscar el hijo por su id
    this.servidor.buscarHijo(this.hijo.idHijo).subscribe(data => {
      this.hijo = data; //Asignamos los datos obtenidos del servidor al objeto hijo
      Swal.fire({
        title: 'Editar',
        icon: 'success',
        text: 'Datos del hijo cargados correctamente',
        showConfirmButton: false,
        timer: 1500
      })
    });
  }

  //Metodo de editar el hijo
  botonEditarHijo() {
    this.servidor.editarHijo(this.hijo).subscribe(data => {
      Swal.fire({
        title: 'Editar',
        icon: 'success',
        text: JSON.stringify(data), //Mostramos la respuesta del servidor(Backend)
        showConfirmButton: false,
        timer: 1500
      });
      this.router.navigate(['listaHijos']); // Navegamos a la lista de hijos después de editar
      });
  }
}

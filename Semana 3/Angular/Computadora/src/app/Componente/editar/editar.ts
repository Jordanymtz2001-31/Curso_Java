import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Servidor } from '../../Servidor/servidor';
import { Computadora } from '../../Entidad/computadora';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar',
  imports: [FormsModule], // Asegúrate de importar FormsModule si usas ngModel
  templateUrl: './editar.html',
  styleUrl: './editar.css',
})
export class Editar implements OnInit {
  

  //Creamos el constructor y le inyectamos el servicio de computadora
  //El ActivatedRoute es para obtener los parámetros de la ruta actual
  constructor(private router : Router, private servidor: Servidor, private route: ActivatedRoute  ) {}

  //Creamos la instancia de la entidad Computadora
  computadora: Computadora = new Computadora();
  error = "";
  
  ngOnInit(): void {
    //Obtenemos el ID de la computadora desde la ruta
    this.route.params.subscribe(params => {
      const id = +params['id']; //Obtenemos el ID de la ruta y lo convertimos a número
      if (id) {
        this.buscar(id); //Llamamos al método buscar para obtener los datos de la computadora
      }else{
        this.error = 'ID de computadora no proporcionado';
      }
    });
  }

  //Método para buscarlo
  buscar(id:number): void {
    this.servidor.buscar(id).subscribe((data) => {
      this.computadora = data;
    });
  }

  //Método para editar la computadora
  editar(): void {
    Swal.fire({
      title: "Esta seguro de editar la computadora?",
      icon: 'warning',
      showCancelButton: true, //Mostrar botón de cancelar
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) { 
        this.servidor.editar(this.computadora).subscribe({
          next: (data) => {
            Swal.fire({
              icon: 'success',
              title: 'Computadora editada',
              text: JSON.stringify(data), //Mostramos el mensaje del backen
              showConfirmButton: false,
              timer: 1500
            });
          }
        });
        //Navegamos al componente listar
        this.router.navigate(['/lista']);
      }// Si ocurre un error mostramos una alerta
      error: (err: any) => {
        Swal.fire({
          icon: 'error',
          title: 'Error al editar la computadora',
          text: JSON.stringify(err), //Mostramos el mensaje del backen
          confirmButtonText : 'Aceptar'
        });
      }
    });
  }

  //Metodo de cancelar
  cancelar(): void {
    this.router.navigate(['/lista']);
  }

}

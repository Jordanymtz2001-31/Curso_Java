import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Servidor } from '../../../Servidor/servidor';
import { Cliente } from '../../../Entidad/cliente';
import Swal from 'sweetalert2';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-guardar-c',
  imports: [FormsModule],
  templateUrl: './guardar-c.html',
  styleUrl: './guardar-c.css',
})
export class GuardarC implements OnInit{

  //Contructor para inicializar con sus inyecciones
  constructor (private router: Router, private servicio: Servidor) {}

  
  Cliente: Cliente = new Cliente() //Intancia de cliente
  clientes: Cliente[] = [] //Creamos una lista para almacenar los clientes

  //Metodo de OnInit
  ngOnInit(): void {
    
  }

  //Boton de Guardar
  btnGuardarCliente(): void{
    this.servicio.guardarClientes(this.Cliente).subscribe({
      next: (response: any) =>{
      Swal.fire({
        title: 'Guardado...✓',
        text: response.body.message || 'Tienda guardada con éxito',
        icon: 'success',
        showConfirmButton: false
      }),
        this.router.navigate(['listar-clientes']); //Navegamos a la lista de clientes despues de guardar
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
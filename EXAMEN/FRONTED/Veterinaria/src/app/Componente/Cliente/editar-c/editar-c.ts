import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from '../../../Entidad/cliente';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-c',
  imports: [FormsModule],
  templateUrl: './editar-c.html',
  styleUrl: './editar-c.css',
})
export class EditarC implements OnInit{

  // Creamos el constructor que inyecta el servicio Servidor
  constructor(private servicio: Servidor, private router: Router, private route: ActivatedRoute) { }

  // Creamos la instancia de la entidad Clientes
  cliente: Cliente = new Cliente(); //Intanciamos con Tienda
  loading = true; //Variable para indicar que si esta cargando
  error = '';

  ngOnInit(): void {
    
    //Obtenerl El iD de la Clientes desde la ruta para cargar los datos
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarClientes(id);
      } else {
        this.error = 'ID de cliente no válido';
        this.loading = false;
      }
    })
  }

  cargarClientes(id: number): void {
    this.servicio.buscarClienteID(id).subscribe({
      next: (Response) => {
        Swal.fire({
          title: 'Cargado...✓'
        });
        this.cliente = Response;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar el Cliente:', err);
        this.error = 'Error al cargar los datos del Cliente';
        this.loading = false;
      }
    });
  }

  btnEditarCliente(): void {
    Swal.fire({
      title: "¿Estás seguro de editar este Cliente?",  
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, editar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.editarCliente(this.cliente).subscribe({
          next: (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Cliente editado', 
              text: response?.message || 'Cliente editado correctamente',
              showConfirmButton: false,
              timer: 1500
            }).then(() => {
              this.router.navigate(['listar/clientes']); 
            });
          },
          error: (err: any) => {  
            Swal.fire({
              icon: 'error',
              title: 'Error al editar Cliente',  
              text: err.error?.error || 'Error desconocido',
              confirmButtonText: 'Aceptar'
            });
          }
        });
      }
    });
  }
}

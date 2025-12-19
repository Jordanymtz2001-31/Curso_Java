import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Departamento } from '../../../Entidad/departamento';
import { Servidor } from '../../../Servidor/servidor';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-d',
  imports: [FormsModule],
  templateUrl: './listar-d.html',
  styleUrl: './listar-d.css',
})
export class ListarD implements OnInit{

  //Set para controlar múltiples menús abiertos simultáneamente
  //Puede contener varios valores de tipo string que representan los menús abiertos
  protected openMenus = new Set<string>();

  //Creamos nuestro contructor y le inyectamos 
  constructor(private router: Router, private servicio:Servidor) {}

  //Creamos variables para almacenar los datos que vienen del servidor
  departamentos!: Departamento[];
  departamento = new Departamento(); //Inicializamos un objeto departamento para usarlo


  //Método para alternar menús
  //Si ago clic en un menú y está abierto, lo cierra
  //Si ago clic en un menú y está cerrado, lo abre
  toggleMenu(menu: string) {
    if (this.openMenus.has(menu)) { //Si el menu esta abierto
      this.openMenus.delete(menu); //Lo cerramos
    } else {
      this.openMenus.add(menu); //Lo abrimos
    }
  }

  //Método para verificar si un menú está abierto
  isMenuOpen(menu: string): boolean {
    return this.openMenus.has(menu);
  }


  //Creamos el metodo ngOnInit para cargar los datos al inicializar el componente
  ngOnInit(): void{
    this.servicio.listarDepartamentos().subscribe({
      next: (data) => { //data es la respuesta del servidor
        this.departamentos = data; //Asignamos los datos a la variable departamentos
      },
      error: (err) => { console.error('Error al cargar los departamentos', err);
      }
    })
  }

  //Metodo para editar una tienda
  btnEditar(id: number){
    this.router.navigate(['editar-departamentos', id]);
  }

  //Metodo para eliminar una tienda
  btnEliminar(id: number){
    Swal.fire({
      title: '¿Estás seguro de eliminar este departamento?',
      text: "¡Esta acción no se puede deshacer!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminarla'
    }).then((result) => {
      if (result.isConfirmed) {
        this.servicio.eliminarDepartamentos(id).subscribe({
          next: (response) => {
            const mensaje = response.body?.message || response.body || 'El departamento ha sido eliminada correctamente';
            Swal.fire(
              'Eliminada',
              mensaje,
              'success'
            );
            this.ngOnInit(); //Recargamos la lista de departamentos despues de eliminar
          },
          error: (err) => {
            const errorMsg = err.error?.error || err.error || 'Hubo un problema al eliminar el departamento';
            Swal.fire(
              'Error',
              errorMsg,
              'error'
            );
          }
        });
      }else if (result.isDismissed) {
        Swal.fire({
          title: 'Cancelado',
          icon : 'info',
          text: 'El departamento no ha sido eliminado',
          showConfirmButton: false,
          timer: 1500
        });
      }
    });
  }

  //Metodo para Listar Productos del Departamento
  btnListProductos(id: number){
    this.router.navigate(['detalles-depaProduc', id]);
  }

  //Metodo para Listar Empleados del Departamento
  btnListEmpleados(id: number){
    this.router.navigate(['detalles-depaEmple', id]);
  }
}

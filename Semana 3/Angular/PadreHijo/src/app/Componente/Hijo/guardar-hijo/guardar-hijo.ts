import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor';
import { Router, RouterLink } from '@angular/router';
import { Hijo } from '../../../Entidad/hijo';
import { Padre } from '../../../Entidad/padre';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-guardar-hijo',
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './guardar-hijo.html',
  styleUrl: './guardar-hijo.css',
})
export class GuardarHijo implements OnInit {

  hijo: Hijo = new Hijo();//Crea una instancia vacía de la clase Hijo
  padres: Padre[] = [];//Array que almacenará la lista de todos los padres registrados
  loading: boolean = false;//Bandera que indica si hay una operación en curso (cargando datos o guardando)
  selectedPadreId: number | null = null;//Almacena el ID del padre seleccionado en el dropdown

  constructor(
    private router: Router,//Servicio para navegar entre páginas
    private service: Servidor,//: Servicio personalizado que hace llamadas HTTP al backend
    private changeDetectorRef: ChangeDetectorRef//Para forzar la detección de cambios en la vista


  ) { }

  ngOnInit(): void {
    this.cargarPadres();

    //Se ejecuta automáticamente cuando el componente se inicializa
    //Llama a cargarPadres() para obtener la lista de padres
  }

  cargarPadres() {
    this.loading = true;//Activa el estado de carga (muestra spinner)
    this.service.listarPadres().subscribe({//Llama al servicio para obtener la lista de padres
      //Se suscribe al Observable para recibir la respuesta
      next: (data) => {// Si la petición es exitosa
        this.padres = data;//Guarda los datos en el array
        this.loading = false;//Desactiva el estado de carga
        this.changeDetectorRef.detectChanges();// Fuerza a Angular a actualizar la vista
      },
      error: (error) => {
        console.error('Error:', error);
        this.loading = false;
        //Muestra error en consola.Desactiva el estado de carga
      }
    });
  }
  //Propósito: Obtener el nombre completo de un padre a partir de su ID
  getPadreNombre(padreId: number | null): string {
    if (!padreId) return 'No seleccionado';

    const padre = this.padres.find(p => p.idPadre === padreId);//Busca en el array el padre con el ID especificado
    return padre ? `${padre.nombre} ${padre.apellido}` : 'No encontrado';
    //Retorna un string con el nombre o mensajes descriptivos
  }


  //Se ejecuta cuando el usuario selecciona un padre en el dropdown, muestra un mensaje con el padre que se selcciono
  onPadreSeleccionado() {
    if (this.selectedPadreId) {
      const padreSeleccionado = this.padres.find(p => p.idPadre === this.selectedPadreId);
      if (padreSeleccionado) {
        this.hijo.idPadre = padreSeleccionado;
      }
    } else {

      this.hijo.idPadre = new Padre();
    }
    /*Si hay un ID seleccionado:
Busca el objeto Padre completo en el array
Si lo encuentra, lo asigna a this.hijo.padreId
Si no hay selección:
sAsigna un objeto Padre vacío */
  }

  guardarHijo() {
    //Validación de padre seleccionado
    if (!this.selectedPadreId) {
      Swal.fire({
        title: 'Error',
        text: 'Debe seleccionar un padre',
        icon: 'warning'
      });
      return;
    }
    //Validación de campos requeridos
    if (!this.hijo.nombre || !this.hijo.apellido) {
      Swal.fire({
        title: 'Error',
        text: 'Nombre y apellido son requeridos',
        icon: 'warning'
      });
      return;
    }
    //Activar estado de carga
    this.loading = true;
    //Llamar al servicio para guardar
    this.service.guardarHijo(this.hijo).subscribe({
      next: (data) => {
        this.loading = false;
        Swal.fire({
          title: '¡Éxito!',
          text: 'Hijo registrado correctamente',
          icon: 'success'
        }).then(() => {
          //regresa a la lista
          this.router.navigate(['/listarHijo']);
        });
      },
      error: (error) => {
        this.loading = false;
        Swal.fire({
          title: 'Error',
          text: 'No se pudo registrar el hijo: ' + error.message,
          icon: 'error'
        });
      }
    });
  }

  cancelar() {
    this.router.navigate(['/listarHijo']);
  } 
}

  //boton por si queremos regresa sin guardar

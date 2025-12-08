import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../Servidor/servidor';
import { Cajero } from '../../Entidad/cajero';
import Swal from 'sweetalert2';

// Definición de la interfaz Denominacion
interface Denominacion {
  tipo: any;
  valor: number;
  cantidad: number;
}

@Component({
  selector: 'app-cajero-a',
  imports: [CommonModule, FormsModule],
  templateUrl: './cajero-a.html',
  styleUrl: './cajero-a.css',
})
export class CajeroA implements OnInit {

  //Creo el cosntructor
  constructor( private service: Servidor, private cdr: ChangeDetectorRef) { }
  
  //Instanciamos el objeto de cajero
  cajero: Cajero = new Cajero();

  //Declaramos variables
  saldo: number = 0;
  cantidadRetirar: number | null = null;
  denominaciones: Denominacion[] = [];
  resultadoRetiro: any = null; // Para almacenar el resultado del retiro

  //Metodo ngOnInit
  ngOnInit(): void {
  this.btnConsultar();
  }

  // Método helper para abrir modales 
  //Le pasamos el id del modal que queremos abrir
  openModal(modalId: string) {
    setTimeout(() => { //Aqui habrimos el modal con un timeout
      const modalElement = document.getElementById(modalId); //Creamos una constante para obtener el id del modal que querramos abrir
      if (modalElement && (window as any).bootstrap?.Modal) { //Verificamos que el modal exista y que bootstrap este cargado

        //Creamos una instancia del modal de bootstrap y le pasamos el elemento del modal que queremos abrir
        //Esto para poder usar los metodos de bootstrap como show() y hide()
        //window as any es para evitar errores de typescript al usar librerias externas como bootstrap 
        const modal = new (window as any).bootstrap.Modal(modalElement);
        modal.show(); //Luego mostramos el modal con el metodo show() con un efecto de bootstrap
        
        // Dependiendo del modal que abrimos 
        //Con addEventListener escuchamos el evento de que el modal se ha mostrado
        //shown.bs.modal es un evento de bootstrap que se dispara cuando el modal se ha mostrado completamente
        modalElement.addEventListener('shown.bs.modal', () => {
          modalElement.focus(); //Y le damos foco al modal para que el usuario pueda interactuar con el
        }, { once: true }); //Indica que el evento se escucha solo una vez
      }
    }, 100); // Pequeño retraso para asegurar que el DOM esté listo(Esperar 100ms)
  }         //Por que angular a veces tarda en renderizar el DOM, sino no funcionaria bien y daria error

  //Metodo para consultar el saldo
  btnConsultar() {
    this.service.Consultar().subscribe((data: any) => {
      this.saldo = data;
      Swal.fire('Saldo', 'Saldo del cajero: $' + data, 'info');
    });
  }

  //Metodo para consultar las denominaciones
  btnDenominaciones() {
    this.service.Denominaciones().subscribe(
      (data: any) => {
        console.log('Denominaciones recibidas:', data);
        //Colocamos el Try Catch para manejar errores
        try {
          // Convertir el formato del backend al formato esperado por el template
          if (Array.isArray(data)) {
            this.denominaciones = data.map((item: any) => ({
              valor: item.denominacion,
              cantidad: item.cantidad,
              tipo: item.tipo
            }));
          } 
          console.log('Denominaciones procesadas:', this.denominaciones);
          
          // Abrimos el modal
          this.openModal('denominacionesModal');
        } catch (e) {
          console.error('Error al procesar denominaciones:', e);
          alert('Error al procesar denominaciones');
        }
      },
      (error) => {
        console.error('Error en denominaciones:', error);
        alert('Error al consultar denominaciones: ' + (error.error || error.message));
      }
    );
  }

  //Metodo para retirar dinero
  //Cuando se da click en el boton retirar
  btnRetirar() {
    this.cantidadRetirar = null; // Variable para almacenar la cantidad a retirar que viene del input
    this.openModal('retirarModal'); // Abrimos el modal de retirar con el metodo openModal
  }

  //Si el usuario confirma el retiro
  btnRetirarConfirm() {
    const cantidad = Number(this.cantidadRetirar); //Convertimos a numero la cantidad a retirar

    //Validamos que no sea nulo o menor o igual a 0
    if (!cantidad || cantidad <= 0) {
      Swal.fire('Error', 'Ingrese una cantidad válida para retirar.', 'error');
      return; //Salir del metodo si la cantidad no es valida
    }

    //Si la cantidad es valida, llamamos al servicio para retirar
    this.service.Retirar(cantidad).subscribe(
      (data: any) => {
        console.log('Data recibida:', data);
        console.log('Tipo de data:', typeof data);
        
        // Parsear si es string
        const resultado = typeof data === 'string' ? JSON.parse(data) : data;
        console.log('Resultado parseado:', resultado);
        
        // Almacenar el resultado del retiro(El monto entregado y las denominaciones)
        this.resultadoRetiro = data;
        console.log('resultadoRetiro asignado:', this.resultadoRetiro);
        
        // Forzar detección de cambios para que Angular actualice la template
        this.cdr.detectChanges();

        // Creamos una constante para almacenar el primer modal (el de retirar)
        const retirarModal = document.getElementById('retirarModal');
        if (retirarModal) { //Si el modal existe
          //Obtenemos la instancia del modal de bootstrap
          const bsModal = (window as any).bootstrap?.Modal.getInstance(retirarModal);
          if (bsModal) { //Si la instancia del modal existe
            bsModal.hide(); // Cerrar el modal
            
            // Esperar a que se cierre y luego abrir el nuevo
            retirarModal.addEventListener('hidden.bs.modal', () => {
              this.openModal('resultadoRetiroModal'); // Abrir el modal de detalle del retiro
            }, { once: true }); //Escuchar solo una vez el evento
          }
        }
        //Despues de retirar, limpiamos la cantidad a retirar
        this.cantidadRetirar = null;
      },
      (error) => {
        console.error('ERROR en subscripción:', error);
        Swal.fire('Error', 'Error al retirar: ' + (error.error || error.message), 'error');
      }
    );
  }

  btnReiniciar() {
    this.service.Reiniciar().subscribe(
      (data: any) => {
        console.log('Respuesta reinicio:', data);
        Swal.fire('Éxito', 'Cajero reiniciado correctamente', 'success');
      },
      (error) => {
        console.error('Error en reinicio:', error);
        Swal.fire('Error', 'Error al reiniciar cajero', 'error');
      }
    );
  }
}

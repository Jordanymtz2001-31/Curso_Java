import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Mascota } from '../../../Entidad/mascota';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-r',
  imports: [FormsModule],
  templateUrl: './detalle-r.html',
  styleUrl: './detalle-r.css',
})
export class DetalleR implements OnInit{

  mascota: Mascota = new Mascota();
  mascotas: Mascota[] = [];
  loading = true; //Variable para indicar que si esta cargando
  error = '';

    constructor(private servicio: Servidor, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

    ngOnInit(): void{
      //Obtenerl El iD del Cliente desde la ruta
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarMascota(id);
      } else {
        this.error = 'ID de la Mascota no válido';
        this.loading = false;
      }
    })
    }

    cargarMascota(id: number): void {
        this.servicio.listarMascotasResponsable(id).subscribe({
          next: (mascotas: Mascota[]) => {
            
            this.cdr.detectChanges();  // FORZAR
            this.mascotas = mascotas;
            if (mascotas.length > 0) {
              Swal.fire({
                title: 'Cargado...✓',
                text: `Se encontraron ${mascotas.length} mascota(s)`,
                timer: 2000,
                showConfirmButton: false
              });
            }else {
              Swal.fire({
                title: 'Sin mascotas',
                text: 'Este Cliente no tiene Mascotas asignados',
                icon: 'info',
                timer: 2500,
                showConfirmButton: false
              });
            }
              this.loading = false;
          },
          error: (err) => {
            this.error = 'Error: ' + (err.error || err.message);
            this.loading = false;
        }
    });
  }

}

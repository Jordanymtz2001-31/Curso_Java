import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Empleado } from '../../../Entidad/empleado';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-empleado',
  imports: [],
  templateUrl: './detalle-empleado.html',
  styleUrl: './detalle-empleado.css',
})
export class DetalleEmpleado implements OnInit{
  empleado: Empleado = new Empleado();
  empleados: Empleado[] = [];
  loading = true; //Variable para indicar que si esta cargando
  error = '';

    constructor(private servicio: Servidor, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

    ngOnInit(): void{
      //Obtenerl El iD del Departamento desde la ruta
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarEmpleados(id);
      } else {
        this.error = 'ID del empleaso no válido';
        this.loading = false;
      }
    })
    }

    cargarEmpleados(id: number): void {
      console.log('ID:', id);
        this.servicio.listarEmpleadosDepartamento(id).subscribe({
          next: (emplaeados: Empleado[]) => {

            this.cdr.detectChanges();  // ← FORZAR
            this.empleados = emplaeados;
            if (emplaeados.length > 0) {
              Swal.fire({
                title: 'Cargado...✓',
                text: `Se encontraron ${emplaeados.length} empleado(s)`,
                timer: 2000,
                showConfirmButton: false
              });
            }else {
              Swal.fire({
                title: 'Sin Empleados',
                text: 'Este departamento no tiene empleados asignados',
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

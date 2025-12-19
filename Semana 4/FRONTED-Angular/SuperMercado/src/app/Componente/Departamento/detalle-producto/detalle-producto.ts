import { Component, OnInit, ChangeDetectorRef  } from '@angular/core';
import { Producto } from '../../../Entidad/producto';
import { Servidor } from '../../../Servidor/servidor';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-detalle-producto',
  imports: [FormsModule],
  templateUrl: './detalle-producto.html',
  styleUrl: './detalle-producto.css',
})

export class DetalleProducto implements OnInit{
  producto: Producto = new Producto();
  productos: Producto[] = [];
  loading = true; //Variable para indicar que si esta cargando
  error = '';

    constructor(private servicio: Servidor, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

    ngOnInit(): void{
      //Obtenerl El iD del Departamento desde la ruta
    this.route.params.subscribe(params =>{
      const id = +params['id']; // El + convierte string a number
      if (id) {
        this.cargarProductos(id);
      } else {
        this.error = 'ID del producto no válido';
        this.loading = false;
      }
    })
    }

    cargarProductos(id: number): void {
      console.log('ID:', id);
      console.log('URL:', this.servicio.url + 'departamento/listarProductos/' + id);
        this.servicio.listarProductosDepartamento(id).subscribe({
          next: (productos: Producto[]) => {
            
            console.log('RESPUESTA RAW:', productos);  // ← CRÍTICO
            console.log('CANTIDAD:', productos.length);
            console.log('PRIMER PRODUCTO:', productos[0]);  // ← VER ESTRUCTURA
            this.cdr.detectChanges();  // ← FORZAR
            this.productos = productos;
            if (productos.length > 0) {
              Swal.fire({
                title: 'Cargado...✓',
                text: `Se encontraron ${productos.length} producto(s)`,
                timer: 2000,
                showConfirmButton: false
              });
            }else {
              Swal.fire({
                title: 'Sin productos',
                text: 'Este departamento no tiene productos asignados',
                icon: 'info',
                timer: 2500,
                showConfirmButton: false
              });
            }
              this.loading = false;
          },
          error: (err) => {
            console.error('ERROR COMPLETO:', err);           // ← ESTO SE EJECUTA
            console.error('STATUS:', err.status);             // ← 404? 500?
            console.error('URL FALLIDA:', err.url);           // ← LA URL REAL
            console.error('RESPONSE:', err.error);            // ← MENSAJE BACKEND
            console.error('Error al cargar el producto:', err);
            this.error = 'Error: ' + (err.error || err.message);
            this.loading = false;
          }
        });
      }

}

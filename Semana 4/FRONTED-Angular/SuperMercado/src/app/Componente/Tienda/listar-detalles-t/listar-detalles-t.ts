// Importaciones necesarias para el funcionamiento del componente
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Servidor } from '../../../Servidor/servidor'; // Servicio para consumir la API
import { ActivatedRoute } from '@angular/router'; // Para obtener parámetros de la ruta
import Swal from 'sweetalert2'; // Para mostrar alertas bonitas
import { Observable } from 'rxjs';
import { Producto } from '../../../Entidad/producto';
import { Empleado } from '../../../Entidad/empleado';
import { Proveedor } from '../../../Entidad/proveedor';
import { Cliente } from '../../../Entidad/cliente';
import { Departamento } from '../../../Entidad/departamento';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listar-detalles-t',
  imports: [FormsModule, CommonModule],
  templateUrl: './listar-detalles-t.html',
  styleUrl: './listar-detalles-t.css',
})
export class ListarDetallesT implements OnInit{

  // ID de la tienda actual, obtenido de la URL
  tiendaId = 0;
  // Pestaña activa (productos, empleados, clientes, proveedores, departamentos)
  activeTab: 'productos' | 'empleados' | 'clientes' | 'proveedores' | 'departamentos' = 'productos';
  // Tipo de entidad que se está mostrando
  tipo = 'productos';
  // Indica si se están cargando los datos
  loading = false;
  // Mensaje para mostrar en la UI (por ejemplo, si no hay datos)
  mensaje = '';

  // Listas para almacenar los objetos de cada entidad
  productos: Producto[] = [];
  empleados: Empleado[] = [];
  clientes: Cliente[] = [];
  proveedores: Proveedor[] = [];
  departamentos: Departamento[] = [];

  // Inyectamos el servicio y la ruta activa
  constructor(private service: Servidor, private route: ActivatedRoute) {}

  // Al inicializar el componente, obtenemos el id de la tienda y mostramos los productos por defecto
  ngOnInit() {
    this.tiendaId = +this.route.snapshot.paramMap.get('id')!;
    this.setTab('productos');
  }

  // Cambia la pestaña activa y carga los datos correspondientes
  setTab(tipo: 'productos' | 'empleados' | 'clientes' | 'proveedores' | 'departamentos') {
    this.activeTab = tipo;
    this.tipo = tipo;
    this.cargarDatos(tipo);
  }

  // Carga los datos de la entidad seleccionada usando el servicio
  cargarDatos(tipo: typeof this.activeTab) {
    this.loading = true;
    // Usamos un switch para decidir qué datos cargar según la pestaña
    switch (tipo) {
      case 'productos':
        // Llama al servicio para obtener los productos de la tienda
        this.service.listarProductosTienda(this.tiendaId).subscribe({
          next: (res) => {
            // Si el backend devuelve una lista correctamente
            if (Array.isArray(res)) {
              this.productos = res;
              // Si la lista está vacía, mostramos un mensaje
              if (this.productos.length === 0) {
                this.mensaje = 'No hay productos en la tienda';
              } else {
                this.mensaje = '';
              }
            } else {
              // Si el backend devuelve un objeto con mensaje de error
              this.mensaje = res?.mensaje || res;
              this.productos = [];
            }
            this.loading = false;
          },
          error: (err: any) => {
            // Captura errores del backend (404, 500, etc.)
            this.loading = false;
            this.empleados = [];
            this.mensaje = typeof err.error === 'string'
              ? err.error
              : err.error?.mensaje || 'Ocurrió un error al cargar productos';
          }
        });
        break;
        
      case 'empleados':
        // Llama al servicio para obtener los empleados de la tienda
        this.service.listarEmpleadosTienda(this.tiendaId).subscribe({
          next: (res) => {
            if (Array.isArray(res)) {
              this.empleados = res;
              if (this.empleados.length === 0) {
                this.mensaje = 'No hay empleados en la tienda';
              } else {
                this.mensaje = '';
              }
            } else {
              this.mensaje = res?.mensaje || res;
              this.empleados = [];
            }
            this.loading = false;
          },
          error: (err: any) => {
            this.loading = false;
            this.empleados = [];
            this.mensaje = typeof err.error === 'string'
              ? err.error
              : err.error?.mensaje || 'Ocurrió un error al cargar empleados';
          }
        });
        break;

      case 'clientes':
        // Llama al servicio para obtener los clientes de la tienda
        this.service.listarClientesTienda(this.tiendaId).subscribe({
          next: (res) => {
            if (Array.isArray(res)) {
              this.clientes = res;
              if (this.clientes.length === 0) {
                this.mensaje = 'No hay clientes en la tienda';
              } else {
                this.mensaje = '';
              }
            } else {
              this.mensaje = res?.mensaje || res;
              this.clientes = [];
            }
            this.loading = false;
          },
          error: (err: any) => {
            this.loading = false;
            this.clientes = [];
            this.mensaje = typeof err.error === 'string'
              ? err.error
              : err.error?.mensaje || 'Ocurrió un error al cargar clientes';
          }
        });
        break;
      
      case 'proveedores':
        // Llama al servicio para obtener los proveedores de la tienda
        this.service.listarProveedoresTienda(this.tiendaId).subscribe({
          next: (res) => {
            if (Array.isArray(res)) {
              this.proveedores = res;
              if (this.proveedores.length === 0) {
                this.mensaje = 'No hay proveedores en la tienda';
              } else {
                this.mensaje = '';
              }
            } else {
              this.mensaje = res?.mensaje || res;
              this.proveedores = [];
            }
            this.loading = false;
          },
          error: (err: any) => {
            this.loading = false;
            this.empleados = [];
            this.mensaje = typeof err.error === 'string'
              ? err.error
              : err.error?.mensaje || 'Ocurrió un error al cargar proveedores';
          }
        });
        break;

      case 'departamentos':
        // Llama al servicio para obtener los departamentos de la tienda
        this.service.listarDepartamentosTienda(this.tiendaId).subscribe({
          next: (res) => {
            if (Array.isArray(res)) {
              this.departamentos = res;
              if (this.departamentos.length === 0) {
                this.mensaje = 'No hay departamentos en la tienda';
              } else {
                this.mensaje = '';
              }
            } else {
              this.mensaje = res?.mensaje || res;
              this.departamentos = [];
            }
            this.loading = false;
          },
          error: (err: any) => {
            this.loading = false;
            this.departamentos = [];
            this.mensaje = typeof err.error === 'string'
              ? err.error
              : err.error?.mensaje || 'Ocurrió un error al cargar departamentos';
          }
        });
        break;
    }
  }

  // Devuelve la cantidad de elementos según la pestaña activa
  getCount(tipo: typeof this.activeTab): number {
    switch (tipo) {
      case 'productos':     return this.productos.length;
      case 'empleados':     return this.empleados.length;
      case 'clientes':      return this.clientes.length;
      case 'proveedores':   return this.proveedores.length;
      case 'departamentos': return this.departamentos.length;
    }
  }
}

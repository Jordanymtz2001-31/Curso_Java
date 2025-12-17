import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Tienda } from '../Entidad/tienda';
import { Observable } from 'rxjs/internal/Observable';
import { Producto } from '../Entidad/producto';
import { Cliente } from '../Entidad/cliente';
import { Departamento } from '../Entidad/departamento';
import { Empleado } from '../Entidad/empleado';
import { Proveedores } from '../Entidad/proveedores';

@Injectable({
  providedIn: 'root',
})
export class Servidor {

  //Creamos un contructor para inicializar el servicio
  //Y le inyectamos las dependencias necesarias
  constructor(private http: HttpClient) {}
  
  //Definimos la URL base del servidor
  url = 'http://localhost:9000/';

  //METODOS DE TIENDA--------------------------------------------------------------------------------
  
  listarTiendas(): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/listar');
  }

  //Observable nos permite observar todas las respuestas del servidor
  //LA MEJOR PRACTICA ES USAR HttpResponse<T> para tipar la respuesta
  guardarTiendas(tienda: Tienda): Observable<any> { //Any para evitar errores y capturar todo tipo de respuestas
    return this.http.post<Tienda>(this.url + 'tiendas/guardar', tienda, { observe: 'response', responseType: 'json'});
  }

  editarTiendas(tienda: Tienda): Observable<HttpResponse<Tienda>> {
    return this.http.patch<Tienda>(this.url + 'tiendas/editar', tienda, { observe: 'response' });
  }

  //Void por que en el backend no nos regresa ningun dato, ni cuerpo
  eliminarTiendas(id: number): Observable<HttpResponse<any>> { //Any por que espero un String
    return this.http.delete<void>(this.url + 'tiendas/eliminar/' + id, { observe: 'response' });
  }

  //Buscar tienda por nombre
  buscarTiendaNombre(nombre: string): Observable<Tienda> {
    return this.http.get<Tienda>(this.url + 'tiendas/buscarNombre' + nombre);
  }

  //Buscar tienda por ID
  buscarTiendaID(id: number): Observable<Tienda> {
    return this.http.get<Tienda>(this.url + 'tiendas/buscar/' + id)
  }

  //Listar tiendas por ciudad
  listarTiendasCiudad(): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/listarCiudad' );
  }

  //Metodo para listar departamentos por tienda
  listarDepartamentosTienda(id: number): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/departamentos' + id);
  }

  //Metodo para listar empleados por tienda
  listarEmpleadosTienda(id: number): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/empleados' + id);
  }

  //Metodo para listar clientes por tienda
  listarClientesTienda(id: number): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/clientes' + id);
  }

  //Metodo para listar proveedores por tienda
  listarProveedoresTienda(id: number): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/proveedores' + id);
  }

  //Metodo para listar productos por tienda
  listarProductosTienda(id: number): Observable<Tienda[]> {
    return this.http.get<Tienda[]>(this.url + 'tiendas/productos' + id);
  }


  //METODOS DE PRODUCTO--------------------------------------------------------------------------------

  listarProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.url + 'productos/listar');
  }

  guardarProductos(producto: Producto): Observable<any> {
    return this.http.post<Producto>(this.url + 'productos/guardar', producto, { observe: 'response', responseType: 'json' });
  }

  editarProductos(producto: Producto): Observable<HttpResponse<Producto>> {
    return this.http.patch<Producto>(this.url + 'productos/editar', producto, { observe: 'response' });
  }

  //Colocamos el void por que en el backend no nos regresa un cuerpo
  eliminarProductos(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<void>(this.url + 'productos/eliminar' + id, { observe: 'response' });
  }

  //Metodo para buscar productos por id
  buscarProductoId(id: number): Observable<Producto> {
    return this.http.get<Producto>(this.url + 'productos/buscar' + id);
  }

  //METODOS PARA CLIENTES--------------------------------------------------------------------------------

  listarClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.url + 'clientes/lista');
  }

  guardarClientes(cliente: Cliente): Observable<any> {
    return this.http.post<Cliente>(this.url + 'clientes/guardar', cliente, { observe: 'response', responseType: 'json' });
  }

  editarClientes(cliente: Cliente): Observable<HttpResponse<Cliente>> {
    return this.http.patch<Cliente>(this.url + 'clientes/editar', cliente, { observe: 'response' });
  }

  //Colocamos el void por que en el backend no nos regresa un cuerpo
  eliminarClientes(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<void>(this.url + 'clientes/eliminar' + id, { observe: 'response' });
  }

  buscarClienteId(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(this.url + 'clientes' + id);
  }

  //METODOS PARA DEPARTAMENTOS--------------------------------------------------------------------------------

  listarDepartamentos(): Observable<Departamento[]> {
    return this.http.get<Departamento[]>(this.url + 'departamento/listar');
  }

  guardarDepartamentos(departamento: Departamento): Observable<any> {
    return this.http.post<Departamento>(this.url + 'departamento/guardar', departamento, { observe: 'response', responseType: 'json' });
  }

  editarDepartamentos(departamento: Departamento): Observable<HttpResponse<Departamento>> {
    return this.http.patch<Departamento>(this.url + 'departamento/editar', departamento, { observe: 'response' });
  }

  //Colocamos el void por que en el backend no nos regresa un cuerpo
  eliminarDepartamentos(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<void>(this.url + 'departamento/eliminar' + id, { observe: 'response' });
  }

  buscarDepartamentoId(id: number): Observable<Departamento> {
    return this.http.get<Departamento>(this.url + 'departamento/buscar' + id);
  }

  //Metodo para listar emplaeados por departamento
  listarEmpleadosDepartamento(id: number): Observable<Departamento[]> {
    return this.http.get<Departamento[]>(this.url + 'departamento/listarEmpleados' + id);
  }

  //Metodo para listar productos por departamento
  listarProductosDepartamento(id: number): Observable<Departamento[]> {
    return this.http.get<Departamento[]>(this.url + 'departamento/listarProductos' + id);
  }

  //METODOS PARA EMPLEADOS--------------------------------------------------------------------------------

  listarEmpleados(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(this.url + 'empleados/listar');
  }

  guardarEmpleados(empleado: Empleado): Observable<any> {
    return this.http.post<Empleado>(this.url + 'empleados/guardar', empleado, { observe: 'response', responseType: 'json' });
  }

  editarEmpleados(empleado: Empleado): Observable<HttpResponse<Empleado>> {
    return this.http.patch<Empleado>(this.url + 'empleados/editar', empleado, { observe: 'response' });
  }

  eliminarEmpleados(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<void>(this.url + 'empleados/eliminar' + id, { observe: 'response' });
  }

  buscarEmpleadoId(id: number): Observable<Empleado> {
    return this.http.get<Empleado>(this.url + 'empleados/buscar' + id);
  }

  //METODOS PARA PROVEEDORES--------------------------------------------------------------------------------
  listarProveedores(): Observable<Proveedores[]> {
    return this.http.get<Proveedores[]>(this.url + 'proveedores/listar');
  }

  guardarProveedores(proveedor: Proveedores): Observable<any> {
    return this.http.post<Proveedores>(this.url + 'proveedores/guardar', proveedor, { observe: 'response', responseType: 'json' });
  }

  editarProveedores(proveedor: Proveedores): Observable<HttpResponse<Proveedores>> {
    return this.http.patch<Proveedores>(this.url + 'proveedores/editar', proveedor, { observe: 'response' });
  }

  //Colocamos el void por que en el backend no nos regresa un cuerpo
  eliminarProveedores(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<void>(this.url + 'proveedores/eliminar' + id, { observe: 'response' });
  }

  buscarProveedorId(id: number): Observable<Proveedores> {
    return this.http.get<Proveedores>(this.url + 'proveedores/buscar' + id);
  }
}

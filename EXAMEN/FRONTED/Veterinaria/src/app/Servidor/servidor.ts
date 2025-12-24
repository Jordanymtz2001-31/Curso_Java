import { HttpClient, HttpClientModule, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cliente } from '../Entidad/cliente';
import { Observable } from 'rxjs';
import { Mascota } from '../Entidad/mascota';
import { Responsable } from '../Entidad/responsable';
import { Veterinaria } from '../Entidad/veterinaria';

@Injectable({
  providedIn: 'root',
})
export class Servidor {

  //Creamos un contructor para inicializar el servicio 
  //Y le inyectamos las dependencias necesarias (HttpClient para hacer solicitudes)
  constructor(private http: HttpClient) {}

  // Definimos la URL base del API Gateway, que actúa como único punto de entrada para las solicitudes
  url = 'http://localhost:9000/';
 
  //METODOS DE CLIENTE--------------------------------------------------------------------------------
  
  listarCliente(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.url + 'cliente/lista');
  }

  //Observable nos permite observar todas las respuestas del servidor
  //LA MEJOR PRACTICA ES USAR HttpResponse<T> para tipar la respuesta
  guardarCliente(cliente: Cliente): Observable<any> { //Any para evitar errores y capturar todo tipo de respuestas
    return this.http.post<Cliente>(this.url + 'cliente/guardar', cliente, { observe: 'response', responseType: 'json'});
  }

  editarCliente(cliente: Cliente): Observable<HttpResponse<Cliente>> {
    return this.http.put<Cliente>(this.url + 'cliente/editar', cliente, { observe: 'response' });
  }

  //Void por que en el backend no nos regresa ningun dato, ni cuerpo
  eliminarCliente(id: number): Observable<HttpResponse<any>> { //Any por que espero un String
    return this.http.delete<void>(this.url + 'cliente/eliminar/' + id, { observe: 'response' });
  }

  //Buscar Cliente por nombre
  buscarClienteNombre(nombre: string): Observable<Cliente> {
    return this.http.get<Cliente>(this.url + 'cliente/buscarNombre' + nombre);
  }

  //Buscar Cliente por ID
  buscarClienteID(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(this.url + 'cliente/buscarId/' + id)
  }

  //Listar Mascotas por Cliente
  listarMascotasCliente(id: number): Observable<any> {
    return this.http.get<any>(this.url + 'cliente/listarMascota/' + id );
  }


  //METODOS DE MASCOTA--------------------------------------------------------------------------------
  
  listarMascota(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(this.url + 'mascota/lista');
  }

  //Observable nos permite observar todas las respuestas del servidor
  //LA MEJOR PRACTICA ES USAR HttpResponse<T> para tipar la respuesta
  guardarMascota(mascota: Mascota): Observable<any> { //Any para evitar errores y capturar todo tipo de respuestas
    return this.http.post<Mascota>(this.url + 'mascota/guardar', mascota, { observe: 'response', responseType: 'json'});
  }

  editarMascota(mascota: Mascota): Observable<HttpResponse<Mascota>> {
    return this.http.put<Mascota>(this.url + 'mascota/editar', mascota, { observe: 'response' });
  }

  //Void por que en el backend no nos regresa ningun dato, ni cuerpo
  eliminarMascota(id: number): Observable<HttpResponse<any>> { //Any por que espero un String
    return this.http.delete<void>(this.url + 'mascota/eliminar/' + id, { observe: 'response' });
  }

  //Buscar Mascota por nombre
  buscarMascotaNombre(nombre: string): Observable<Mascota> {
    return this.http.get<Mascota>(this.url + 'mascota/buscarNombre' + nombre);
  }

  //Buscar Cliente por ID
  buscarMascotaID(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(this.url + 'mascota/buscarId/' + id)
  }

  //METODOS DE RESPONSABLE--------------------------------------------------------------------------------
  
  listarResponsable(): Observable<Responsable[]> {
    return this.http.get<Responsable[]>(this.url + 'responsable/lista');
  }

  //Observable nos permite observar todas las respuestas del servidor
  //LA MEJOR PRACTICA ES USAR HttpResponse<T> para tipar la respuesta
  guardarResponsable(responsable: Responsable): Observable<any> { //Any para evitar errores y capturar todo tipo de respuestas
    return this.http.post<Responsable>(this.url + 'responsable/guardar', responsable, { observe: 'response', responseType: 'json'});
  }

  editarResponsable(responsable: Responsable): Observable<HttpResponse<Responsable>> {
    return this.http.put<Responsable>(this.url + 'responsable/editar', responsable, { observe: 'response' });
  }

  //Void por que en el backend no nos regresa ningun dato, ni cuerpo
  eliminarResponsable(id: number): Observable<HttpResponse<any>> { //Any por que espero un String
    return this.http.delete<void>(this.url + 'responsable/eliminar/' + id, { observe: 'response' });
  }

  //Buscar Responsable por nombre
  buscarResponsableNombre(nombre: string): Observable<Responsable> {
    return this.http.get<Responsable>(this.url + 'responsable/buscarNombre' + nombre);
  }

  //Buscar Responsable por ID
  buscarResponsableID(id: number): Observable<Responsable> {
    return this.http.get<Responsable>(this.url + 'responsable/buscarId/' + id)
  }

  //Listar Mascotas por Responsable
  listarMascotasResponsable(id: number): Observable<any> {
    return this.http.get<any>(this.url + 'responsable/listarMascota/' + id );
  }

  //METODOS DE VETERINARIA--------------------------------------------------------------------------------
  
  listarVeterinaria(): Observable<Veterinaria[]> {
    return this.http.get<Veterinaria[]>(this.url + 'veterinaria/lista');
  }

  //Observable nos permite observar todas las respuestas del servidor
  //LA MEJOR PRACTICA ES USAR HttpResponse<T> para tipar la respuesta
  guardarVeterinaria(veterinaria: Veterinaria): Observable<any> { //Any para evitar errores y capturar todo tipo de respuestas
    return this.http.post<Veterinaria>(this.url + 'veterinaria/guardar', veterinaria, { observe: 'response', responseType: 'json'});
  }

  editarVeterinaria(veterinaria: Veterinaria): Observable<HttpResponse<Veterinaria>> {
    return this.http.put<Veterinaria>(this.url + 'veterinaria/editar', veterinaria, { observe: 'response' });
  }

  //Void por que en el backend no nos regresa ningun dato, ni cuerpo
  eliminarVeterinaria(id: number): Observable<HttpResponse<any>> { //Any por que espero un String
    return this.http.delete<void>(this.url + 'veterinaria/eliminar/' + id, { observe: 'response' });
  }

  //Buscar Veterinaria por nombre
  buscarVeterinariaNombre(nombre: string): Observable<Veterinaria> {
    return this.http.get<Veterinaria>(this.url + 'veterinaria/buscarNombre' + nombre);
  }

  //Buscar Veterinaria por ID
  buscarVeterinariaID(id: number): Observable<Responsable> {
    return this.http.get<Responsable>(this.url + 'veterinaria/buscarId/' + id)
  }

  //Listar Mascotas por Veterinaria
  listarMascotasVeterinaria(id: number): Observable<any> {
    return this.http.get<any>(this.url + 'veterinaria/mascotasList/' + id );
  }

  //Listar Responsables por Veterinaria
  listarResponsablesVeterinaria(id: number): Observable<any> {
    return this.http.get<any>(this.url + 'veterinaria/responsablesList/' + id );
  }
}

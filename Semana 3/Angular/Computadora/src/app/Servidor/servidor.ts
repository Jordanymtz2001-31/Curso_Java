import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Computadora } from '../Entidad/computadora';

@Injectable({
  providedIn: 'root',
})
export class Servidor {

  //Creamos el constructor y inyectamos el servicio de HttpClient
  constructor(private http: HttpClient) {}
  
  //Definimos la URL base del servidor
  //Es decir hacer las peticiones a esta URL
  private url = 'http://localhost:8001/api/computadoras';

  listar() {
    return this.http.get<Computadora[]>(this.url + "/lista");
  }

  guardar(computadora: Computadora) { //El responseType es para indicar el tipo de respuesta que esperamos del servidor 
    return this.http.post<Computadora>(this.url + "/guardar", computadora, {responseType: 'text' as 'json'});
  }

  editar(computadora: Computadora) {
    return this.http.put<Computadora>(this.url + "/editar", computadora, {responseType: 'text' as 'json'});
  }

  eliminar(id: number) {
    return this.http.delete(this.url + "/eliminar/" + id, {responseType: 'text' as 'json'});
  }
}

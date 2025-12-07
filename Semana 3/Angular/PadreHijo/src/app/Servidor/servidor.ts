import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Padre } from '../Entidad/padre';
import { Hijo } from '../Entidad/hijo';

@Injectable({
  providedIn: 'root',
})
export class Servidor {

  //Creamos un constructor y le inyetamos el HttpClient
  constructor(private http: HttpClient) {}

  //Definimos la URL base del servidor
  url = 'http://localhost:8002';
  
  //Metodo para obtener todos los padres
  listarPadres() {
    return this.http.get<Padre[]>(this.url + '/padre/listar');
  }

  //Metodo para guardar un padre
  guardarPadre(padre: Padre) {
    return this.http.post<String>(this.url + '/padre/guardar', padre, {responseType: 'text' as 'json'});
  }

  //Metodo para buscar
  buscarPadre(idPadre: number) {
    return this.http.get<Padre>(this.url + '/padre/buscar/' + idPadre);
  }

  //Metodo para editar un padre
  editarPadre(padre: Padre) { //El responseType es para indicar que el servidor devuelve un texto y no un JSON
    return this.http.put<String>(this.url + '/padre/editar', padre, {responseType: 'text' as 'json'});
  }

  //Metodo para eliminar un padre
  eliminarPadre(idPadre: number) {
    return this.http.delete<String>(this.url + '/padre/eliminar/' + idPadre, {responseType: 'text' as 'json'});
  }
  
  //--------------------------------------------------------------------------------------------------
  //Metodo para obtener todos los hijos de un padre
  listarHijos(){
    return this.http.get<Hijo[]>(this.url + '/hijo/listar');
  }

  //Metodo para guardar un hijo
  guardarHijo(hijo: Hijo) { //El responseType es para indicar que el servidor devuelve un texto y no un JSON
    return this.http.post<Hijo>(this.url + '/hijo/guardar', hijo, {responseType: 'text' as 'json'});
  }

  //Metodo para buscar un hijo por su id
  buscarHijo(idHijo: number) {
    return this.http.get<Hijo>(this.url + '/hijo/buscar/' + idHijo)
  }

  //Metodo para editar un hijo
  editarHijo(hijo: Hijo){ //Colocamos Hijo para indicar que el servidor devuelve un objeto Hijo
    return this.http.put<Hijo>(this.url + '/hijo/editar', hijo, {responseType: 'text' as 'json'});
  }

  //Metodo para eliminar un hijo
  eliminarHijo(idHijo: number){ //Pasamos String para indicar que el servidor devuelve un texto y no un JSON
    return this.http.delete<String>(this.url + '/hijo/eliminar/' + idHijo, {responseType: 'text' as 'json'});
  }

  
}

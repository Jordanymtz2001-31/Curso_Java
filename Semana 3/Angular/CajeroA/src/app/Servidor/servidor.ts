import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Servidor {

  //Creamos un constructor y le inyectamos el HttpClient
  constructor(private http: HttpClient) {}

  //Definimos la URL del servidor
  url = 'http://localhost:8002';

  //Metodo para consultar el saldo
  Consultar() {
    return this.http.get(this.url + '/cajero/saldo', { responseType: 'json' });
  }

  //Metodo para consultar denominaciones
  Denominaciones() {
    return this.http.get(this.url + '/cajero/inventario', { responseType: 'json' });
  }

  //Metodo para retirar dinero
  Retirar(cantidad: number) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.url + '/cajero/retirar', { montoRetiro: cantidad }, { headers, responseType: 'json' });
  }

  //Metodo para reiniciar el cajero
  Reiniciar() {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.url + '/cajero/reiniciar', {}, { headers, responseType: 'text' });
  }

  
}

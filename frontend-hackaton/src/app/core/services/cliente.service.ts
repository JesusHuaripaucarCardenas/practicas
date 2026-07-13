import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../interfaces/cliente.interface';

@Injectable({ providedIn: 'root' })
export class ClienteService {

  private baseUrl = 'https://fuzzy-happiness-69rx57jv4xv534x7v-30082.app.github.dev/api/clientes';

  constructor(private http: HttpClient) {}

  listar(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl);
  }

  crear(c: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.baseUrl, c);
  }

  actualizar(id: number, c: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}`, c);
  }

  eliminar(id: number): Observable<Cliente> {
    return this.http.patch<Cliente>(`${this.baseUrl}/${id}/eliminar`, {});
  }

  restaurar(id: number): Observable<Cliente> {
    return this.http.patch<Cliente>(`${this.baseUrl}/${id}/restaurar`, {});
  }
}
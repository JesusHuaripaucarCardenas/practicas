import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Alquiler } from '../interfaces/alquiler.interface';

@Injectable({ providedIn: 'root' })
export class AlquilerService {

  private baseUrl = 'https://fuzzy-happiness-69rx57jv4xv534x7v-30083.app.github.dev/api/alquileres'; 

  constructor(private http: HttpClient) {}

  listar(): Observable<Alquiler[]> {
    return this.http.get<Alquiler[]>(this.baseUrl);
  }

  crear(a: Alquiler): Observable<Alquiler> {
    return this.http.post<Alquiler>(this.baseUrl, a);
  }

  actualizar(id: number, a: Alquiler): Observable<Alquiler> {
    return this.http.put<Alquiler>(`${this.baseUrl}/${id}`, a);
  }

  eliminar(id: number): Observable<Alquiler> {
    return this.http.patch<Alquiler>(`${this.baseUrl}/${id}/eliminar`, {});
  }

  restaurar(id: number): Observable<Alquiler> {
    return this.http.patch<Alquiler>(`${this.baseUrl}/${id}/restaurar`, {});
  }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehiculo } from '../interfaces/vehiculo.interface';

@Injectable({ providedIn: 'root' })
export class VehiculoService {

  private baseUrl = 'https://upgraded-meme-r4r56vj7px9x2xp7r-30081.app.github.dev/api/vehiculos';

  constructor(private http: HttpClient) {}

  listar(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(this.baseUrl);
  }

  crear(v: Vehiculo): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(this.baseUrl, v);
  }

  actualizar(id: number, v: Vehiculo): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.baseUrl}/${id}`, v);
  }

  eliminar(id: number): Observable<Vehiculo> {
    return this.http.patch<Vehiculo>(`${this.baseUrl}/${id}/eliminar`, {});
  }

  restaurar(id: number): Observable<Vehiculo> {
    return this.http.patch<Vehiculo>(`${this.baseUrl}/${id}/restaurar`, {});
  }
}
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import { Producto } from '../interfaces/producto.interface';

// @Injectable({ providedIn: 'root' }) hace que Angular cree UNA sola
// instancia de este servicio y la comparta en toda la app (singleton),
// sin que tengas que registrarlo manualmente en ningún módulo.
@Injectable({ providedIn: 'root' })
export class ProductoService {
  // inject() es la forma moderna de pedirle a Angular una dependencia
  // (HttpClient) sin necesidad de usar el constructor.
  private http = inject(HttpClient);

  // Ojo: este backend corre en el puerto 8081 (distinto al de Practica,
  // que era 8080). Por eso la URL base usa environment.productoApiUrl
  // en vez de environment.apiUrl.
  private baseUrl = `${environment.productoApiUrl}/api/producto`;

  // Trae TODOS los productos, activos e inactivos.
  findAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrl);
  }

  // Trae solo los productos con active = true.
  findAllActive(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.baseUrl}/active`);
  }

  // Trae solo los productos con active = false (eliminados lógicamente).
  findAllInactive(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.baseUrl}/inactive`);
  }

  // Busca un producto puntual por su id.
  findById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/${id}`);
  }

  // Crea un producto nuevo. El backend se encarga de poner active = true.
  create(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.baseUrl, producto);
  }

  // Actualiza nombre, precio y stock de un producto existente.
  update(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.baseUrl}/${id}`, producto);
  }

  // Borrado LÓGICO: no elimina la fila de la base de datos, solo pone
  // active = false. Por eso el método HTTP es PATCH, no DELETE.
  logicalDelete(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/delete`, {});
  }

  // Restaura un producto previamente eliminado (active = true de nuevo).
  restore(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/restore`, {});
  }
}
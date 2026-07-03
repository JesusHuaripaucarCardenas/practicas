import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Necesario para usar [(ngModel)] en el HTML
import { Producto } from '../../core/interfaces/producto.interface';
import { ProductoService } from '../../core/services/producto.service';

// Tipo simple para controlar qué lista se está mostrando.
type Filtro = 'todos' | 'activos' | 'inactivos';

@Component({
  selector: 'app-producto',
  standalone: true,               // No necesita NgModule, se auto-declara
  imports: [CommonModule, FormsModule],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.scss'
})
export class ProductoComponent implements OnInit {
  // Inyectamos el servicio para poder llamar a la API.
  private productoService = inject(ProductoService);

  // Lista que se pinta en la tabla.
  productos: Producto[] = [];

  // Filtro activo actualmente (por defecto muestra todos).
  filtro: Filtro = 'todos';

  // Flags de estado para mostrar mensajes en pantalla.
  cargando = false;
  error = '';

  // Objeto enlazado al formulario de creación (con [(ngModel)]).
  nuevoProducto: Producto = this.productoVacio();

  // Se ejecuta una sola vez, apenas el componente se monta.
  ngOnInit(): void {
    this.cargar();
  }

  // Devuelve un objeto Producto "en blanco" para resetear el formulario.
  private productoVacio(): Producto {
    return { nombre: '', precio: 0, stock: 0 };
  }

  // Carga la lista según el filtro seleccionado.
  cargar(): void {
    this.cargando = true;
    this.error = '';

    // Elegimos qué endpoint llamar según el filtro actual.
    const peticion =
      this.filtro === 'activos' ? this.productoService.findAllActive() :
      this.filtro === 'inactivos' ? this.productoService.findAllInactive() :
      this.productoService.findAll();

    peticion.subscribe({
      next: (data) => { this.productos = data; this.cargando = false; },
      error: () => { this.error = 'No se pudo cargar la lista.'; this.cargando = false; }
    });
  }

  // Cambia el filtro y vuelve a pedir los datos al backend.
  cambiarFiltro(filtro: Filtro): void {
    this.filtro = filtro;
    this.cargar();
  }

  // Envía el formulario de creación.
  crear(): void {
    // Validación mínima en el front, antes de golpear la API.
    if (!this.nuevoProducto.nombre || this.nuevoProducto.precio <= 0) {
      this.error = 'Completa nombre y un precio válido.';
      return;
    }
    this.productoService.create(this.nuevoProducto).subscribe({
      next: () => {
        this.nuevoProducto = this.productoVacio(); // Limpia el formulario
        this.cargar();                             // Refresca la tabla
      },
      error: () => { this.error = 'No se pudo crear el producto.'; }
    });
  }

  // Borrado lógico: marca active = false.
  eliminar(id?: number): void {
    if (!id) return;
    this.productoService.logicalDelete(id).subscribe({
      next: () => this.cargar(),
      error: () => { this.error = 'No se pudo eliminar.'; }
    });
  }

  // Restaura: vuelve a marcar active = true.
  restaurar(id?: number): void {
    if (!id) return;
    this.productoService.restore(id).subscribe({
      next: () => this.cargar(),
      error: () => { this.error = 'No se pudo restaurar.'; }
    });
  }
}
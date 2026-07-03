import { Routes } from '@angular/router';
import { ProductoComponent } from './feature/ejemplos/producto.component';

// Cada entidad tiene su propia ruta. Puedes agregar tantas como
// backends/pantallas vayas conectando, siguiendo el mismo patrón.
export const routes: Routes = [
  { path: 'producto', component: ProductoComponent },
  { path: '', redirectTo: 'practica', pathMatch: 'full' } // Ruta por defecto
];
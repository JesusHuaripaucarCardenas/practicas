import { Routes } from '@angular/router';
import { VehiculoComponent } from './feature/vehiculo/vehiculo.component';
import { ClienteComponent } from './feature/cliente/cliente.component';
import { AlquilerComponent } from './feature/alquiler/alquiler.component';

export const routes: Routes = [
  { path: '', redirectTo: 'vehiculos', pathMatch: 'full' },
  { path: 'vehiculos', component: VehiculoComponent },
  { path: 'clientes', component: ClienteComponent },
  { path: 'alquileres', component: AlquilerComponent }
];

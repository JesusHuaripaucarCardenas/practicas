import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { VehiculoService } from '../../core/services/vehiculo.service';
import { Vehiculo } from '../../core/interfaces/vehiculo.interface';

@Component({
  selector: 'app-vehiculo',
  standalone: true,
  imports: [CommonModule, FormsModule, DataTableComponent],
  templateUrl: './vehiculo.component.html',
  styleUrls: ['./vehiculo.component.scss']
})
export class VehiculoComponent implements OnInit {
  items = signal<Vehiculo[]>([]);
  form: Partial<Vehiculo> = {};
  editando: number | null = null;

  columnas = [
    { key: 'placa', label: 'Placa' },
    { key: 'marca', label: 'Marca' },
    { key: 'modelo', label: 'Modelo' },
    { key: 'anio', label: 'Año' },
    { key: 'color', label: 'Color' },
    { key: 'precioPorDia', label: 'Precio/dia' }
  ];

  constructor(private service: VehiculoService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.service.listar().subscribe({
      next: data => this.items.set(data),
      error: err => console.error('Error al listar vehiculos:', err)
    });
  }

  guardar(): void {
    if (this.editando) {
      this.service.actualizar(this.editando, this.form as Vehiculo).subscribe({
        next: () => { this.cancelar(); this.cargar(); },
        error: err => console.error('Error al actualizar vehiculo:', err)
      });
    } else {
      this.service.crear(this.form as Vehiculo).subscribe({
        next: () => { this.form = {}; this.cargar(); },
        error: err => console.error('Error al crear vehiculo:', err)
      });
    }
  }

  editar(item: Vehiculo): void {
    this.form = { ...item };
    this.editando = item.id!;
  }

  cancelar(): void {
    this.form = {};
    this.editando = null;
  }

  eliminar(id: number): void {
    this.service.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al eliminar vehiculo:', err)
    });
  }

  restaurar(id: number): void {
    this.service.restaurar(id).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al restaurar vehiculo:', err)
    });
  }

  toggleMantenimiento(item: Vehiculo): void {
    const nuevoEstado = item.estado === 'MANTENIMIENTO' ? 'DISPONIBLE' : 'MANTENIMIENTO';
    const actualizado: Vehiculo = { ...item, estado: nuevoEstado };

    this.service.actualizar(item.id!, actualizado).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al cambiar estado del vehiculo:', err)
    });
  }
}
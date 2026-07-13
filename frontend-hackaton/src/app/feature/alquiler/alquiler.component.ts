import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { AlquilerService } from '../../core/services/alquiler.service';
import { Alquiler } from '../../core/interfaces/alquiler.interface';

@Component({
  selector: 'app-alquiler',
  standalone: true,
  imports: [CommonModule, FormsModule, DataTableComponent],
  templateUrl: './alquiler.component.html',
  styleUrls: ['./alquiler.component.scss']
})
export class AlquilerComponent implements OnInit {
  items = signal<Alquiler[]>([]);
  form: Partial<Alquiler> = {};
  editando: number | null = null;

  columnas = [
    { key: 'clienteId', label: 'ID Cliente' },
    { key: 'vehiculoId', label: 'ID Vehiculo' },
    { key: 'dias', label: 'Dias' },
    { key: 'fechaInicio', label: 'Fecha inicio' },
    { key: 'fechaFin', label: 'Fecha fin' },
    { key: 'total', label: 'Total' }
  ];

  constructor(private service: AlquilerService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.service.listar().subscribe({
      next: data => this.items.set(data),
      error: err => console.error('Error al listar alquileres:', err)
    });
  }

  guardar(): void {
    if (this.editando) {
      this.service.actualizar(this.editando, this.form as Alquiler).subscribe({
        next: () => { this.cancelar(); this.cargar(); },
        error: err => console.error('Error al actualizar alquiler:', err)
      });
    } else {
      this.service.crear(this.form as Alquiler).subscribe({
        next: () => { this.form = {}; this.cargar(); },
        error: err => console.error('Error al crear alquiler:', err)
      });
    }
  }

  editar(item: Alquiler): void {
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
      error: err => console.error('Error al eliminar alquiler:', err)
    });
  }

  restaurar(id: number): void {
    this.service.restaurar(id).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al restaurar alquiler:', err)
    });
  }
}
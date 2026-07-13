import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { ClienteService } from '../../core/services/cliente.service';
import { Cliente } from '../../core/interfaces/cliente.interface';

@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule, DataTableComponent],
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.scss']
})
export class ClienteComponent implements OnInit {
  items = signal<Cliente[]>([]);
  form: Partial<Cliente> = {};
  editando: number | null = null;

  columnas = [
    { key: 'dni', label: 'DNI' },
    { key: 'nombres', label: 'Nombres' },
    { key: 'apellidos', label: 'Apellidos' },
    { key: 'celular', label: 'Celular' },
    { key: 'correo', label: 'Correo' },
    { key: 'licencia', label: 'Licencia' }
  ];

  constructor(private service: ClienteService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.service.listar().subscribe({
      next: data => this.items.set(data),
      error: err => console.error('Error al listar clientes:', err)
    });
  }

  guardar(): void {
    if (this.editando) {
      this.service.actualizar(this.editando, this.form as Cliente).subscribe({
        next: () => { this.cancelar(); this.cargar(); },
        error: err => console.error('Error al actualizar cliente:', err)
      });
    } else {
      this.service.crear(this.form as Cliente).subscribe({
        next: () => { this.form = {}; this.cargar(); },
        error: err => console.error('Error al crear cliente:', err)
      });
    }
  }

  editar(item: Cliente): void {
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
      error: err => console.error('Error al eliminar cliente:', err)
    });
  }

  restaurar(id: number): void {
    this.service.restaurar(id).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al restaurar cliente:', err)
    });
  }
}
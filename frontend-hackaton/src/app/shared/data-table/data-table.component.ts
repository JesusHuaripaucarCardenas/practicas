import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.scss']
})
export class DataTableComponent {
  @Input() columnas: { key: string; label: string }[] = [];
  @Input() items: any[] = [];
  @Input() mostrarMantenimiento = false;
  @Output() editar = new EventEmitter<any>();
  @Output() eliminar = new EventEmitter<number>();
  @Output() restaurar = new EventEmitter<number>();
  @Output() mantenimiento = new EventEmitter<any>();
}
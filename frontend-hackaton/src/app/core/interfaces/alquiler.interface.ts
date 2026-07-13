export interface Alquiler {
  id?: number;
  clienteId: number;
  vehiculoId: number;
  dias: number;
  fechaInicio: string;
  fechaFin: string;
  total: number;
  estado?: string;
}

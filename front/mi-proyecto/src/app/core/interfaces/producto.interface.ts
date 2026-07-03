// Representa la forma exacta del objeto Producto que viaja entre el
// front y el back. Debe reflejar los mismos campos que la clase
// Producto.java del backend (nombre, precio, stock, active).
export interface Producto {
  id?: number;         // Opcional: no existe hasta que el backend lo crea
  nombre: string;
  precio: number;
  stock: number;
  active?: boolean;    // Opcional: el backend lo pone en true al crear
}
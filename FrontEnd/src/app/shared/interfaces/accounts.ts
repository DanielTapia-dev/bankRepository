import { Movement } from './movements';

export interface Account {
  id?: number;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  estado: boolean;
  clienteId: number;
  clienteNombre: string;
  clienteIdentificacion: string;
  movimientos?: Movement[];
}

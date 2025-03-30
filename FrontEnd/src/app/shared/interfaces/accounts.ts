import { Movimiento } from './movimientos';

export interface Account {
  id?: number;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  estado: boolean;
  clienteId: number;
  clienteNombre: string;
  clienteIdentificacion: string;
  movimientos?: Movimiento[];
}

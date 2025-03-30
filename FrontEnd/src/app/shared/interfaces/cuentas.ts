import { Cliente } from './cliente';
import { Movimiento } from './movimientos';

export interface Cuenta {
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

export interface Movement {
  id?: number;
  fecha: string;
  tipoMovimiento: string;
  valor: number;
  saldo: number;
  cuentaId: number;
  saldoActual: number;
  clienteNombre: number;
  estadoCuenta: string;
}

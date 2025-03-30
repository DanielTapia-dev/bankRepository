import { Cuenta } from './cuentas';

export interface Persona {
  id: number;
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
}

export interface Cliente extends Persona {
  contrasena: string;
  estado: boolean;
  cuentas?: Cuenta[];
}

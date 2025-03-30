import { Account } from './accounts';

export interface Person {
  id: number;
  nombre: string;
  genero: string;
  edad: number;
  identificacion: string;
  direccion: string;
  telefono: string;
}

export interface Client extends Person {
  contrasena: string;
  estado: boolean;
  cuentas?: Account[];
}

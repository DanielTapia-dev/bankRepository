import { Component, OnInit } from '@angular/core';
import { Cuenta } from 'src/app/shared/interfaces/cuentas';
import { CuentaService } from './services/cuenta.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-cuentas',
  templateUrl: './cuentas.component.html',
  styleUrls: ['./cuentas.component.scss'],
})
export class CuentasComponent implements OnInit {
  cuentas: Cuenta[] = [];

  constructor(
    private cuentasService: CuentaService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadCuentas();
  }

  loadCuentas() {
    this.cuentasService.getAll().subscribe((resp) => {
      this.cuentas = resp;
      console.log(resp);
    });
  }

  editCuenta(cuenta: Cuenta) {
    console.log('Editar cliente:', cuenta);
  }

  deleteCuenta(id: number) {
    this.cuentasService.delete(id).subscribe(() => {
      this.alertService.success('Cuenta eliminada');
      this.loadCuentas();
    });
  }
}

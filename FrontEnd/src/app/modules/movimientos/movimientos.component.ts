import { Component, OnInit } from '@angular/core';
import { Movimiento } from 'src/app/shared/interfaces/movimientos';
import { MovimientoService } from './services/movimiento.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-movimientos',
  templateUrl: './movimientos.component.html',
  styleUrls: ['./movimientos.component.scss'],
})
export class MovimientosComponent implements OnInit {
  movimientos: Movimiento[] = [];

  constructor(
    private movimientosService: MovimientoService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadCuentas();
  }

  loadCuentas() {
    this.movimientosService.getAll().subscribe((resp) => {
      this.movimientos = resp;
      console.log(resp);
    });
  }

  editMovimiento(movimiento: Movimiento) {
    console.log('Editar cliente:', movimiento);
  }

  deleteMovimiento(id: number) {
    this.movimientosService.delete(id).subscribe(() => {
      this.alertService.success('Movimiento eliminada');
      this.loadCuentas();
    });
  }
}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovimientoFormComponent } from './movimiento-form/movimiento-form.component';
import { MovimientosComponent } from './movimientos.component';

@NgModule({
  declarations: [MovimientosComponent, MovimientoFormComponent],
  imports: [CommonModule],
  exports: [MovimientosComponent],
})
export class MovimientosModule {}

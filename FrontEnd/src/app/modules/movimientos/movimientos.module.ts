import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovimientoFormComponent } from './movimiento-form/movimiento-form.component';
import { MovimientosComponent } from './movimientos.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [MovimientosComponent, MovimientoFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [MovimientosComponent],
})
export class MovimientosModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovimientosComponent } from './movimientos/movimientos.component';
import { MovimientoFormComponent } from './movimiento-form/movimiento-form.component';



@NgModule({
  declarations: [
    MovimientosComponent,
    MovimientoFormComponent
  ],
  imports: [
    CommonModule
  ]
})
export class MovimientosModule { }

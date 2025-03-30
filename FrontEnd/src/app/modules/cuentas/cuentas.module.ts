import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CuentasComponent } from './cuentas/cuentas.component';
import { CuentaFormComponent } from './cuenta-form/cuenta-form.component';



@NgModule({
  declarations: [
    CuentasComponent,
    CuentaFormComponent
  ],
  imports: [
    CommonModule
  ]
})
export class CuentasModule { }

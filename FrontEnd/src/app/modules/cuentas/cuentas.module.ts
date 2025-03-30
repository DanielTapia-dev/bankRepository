import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CuentaFormComponent } from './cuenta-form/cuenta-form.component';
import { CuentasComponent } from './cuentas.component';

@NgModule({
  declarations: [CuentasComponent, CuentaFormComponent],
  imports: [CommonModule],
  exports: [CuentasComponent],
})
export class CuentasModule {}

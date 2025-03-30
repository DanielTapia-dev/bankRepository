import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CuentaFormComponent } from './cuenta-form/cuenta-form.component';
import { CuentasComponent } from './cuentas.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [CuentasComponent, CuentaFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [CuentasComponent],
})
export class CuentasModule {}

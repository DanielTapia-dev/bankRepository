import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CuentaFormComponent } from './cuenta-form/cuenta-form.component';
import { AccountsComponent } from './cuentas.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [AccountsComponent, CuentaFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [AccountsComponent],
})
export class CuentasModule {}

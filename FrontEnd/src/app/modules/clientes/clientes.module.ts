import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClienteFormComponent } from './cliente-form/cliente-form.component';
import { ClientesComponent } from './clientes.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [ClientesComponent, ClienteFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [ClientesComponent],
})
export class ClientesModule {}

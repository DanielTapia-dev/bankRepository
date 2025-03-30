import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClienteFormComponent } from './cliente-form/cliente-form.component';
import { ClientesComponent } from './clientes.component';

@NgModule({
  declarations: [ClientesComponent, ClienteFormComponent],
  imports: [CommonModule],
})
export class ClientesModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientFormComponent } from './clients-form/client-form.component';
import { ClientsComponent } from './clients.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [ClientsComponent, ClientFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [ClientsComponent],
})
export class ClientsModule {}

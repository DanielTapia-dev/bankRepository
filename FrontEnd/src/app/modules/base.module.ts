import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseComponent } from './base.component';
import { RouterModule } from '@angular/router';
import { HeaderModule } from '../layout/header/header.module';
import { SidebarModule } from '../layout/sidebar/sidebar.module';
import { BaseRoutingModule } from './base-routing.module';
import { ReportesModule } from './reportes/reportes.module';
import { SharedModule } from '../shared/shared.module';
import { ClientsModule } from './clients/clients.module';
import { AccountsModule } from './accounts/accounts.module';
import { MovementsModule } from './movimientos/movements.module';

@NgModule({
  declarations: [BaseComponent],
  imports: [
    CommonModule,
    RouterModule,
    BaseRoutingModule,
    HeaderModule,
    SidebarModule,
    ClientsModule,
    AccountsModule,
    MovementsModule,
    ReportesModule,
    SharedModule,
  ],
})
export class BaseModule {}

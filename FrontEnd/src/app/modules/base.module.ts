import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseComponent } from './base.component';
import { RouterModule } from '@angular/router';
import { HeaderModule } from '../layout/header/header.module';
import { SidebarModule } from '../layout/sidebar/sidebar.module';
import { BaseRoutingModule } from './base-routing.module';
import { ClientesModule } from './clientes/clientes.module';
import { CuentasModule } from './cuentas/cuentas.module';
import { MovimientosModule } from './movimientos/movimientos.module';
import { ReportesModule } from './reportes/reportes.module';

@NgModule({
  declarations: [BaseComponent],
  imports: [
    CommonModule,
    RouterModule,
    BaseRoutingModule,
    HeaderModule,
    SidebarModule,
    ClientesModule,
    CuentasModule,
    MovimientosModule,
    ReportesModule,
  ],
})
export class BaseModule {}

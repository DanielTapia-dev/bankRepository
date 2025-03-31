import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BaseComponent } from './base.component';
import { AccountsComponent } from './accounts/accounts.component';
import { MovementsComponent } from './movimientos/movements.component';
import { ReportesComponent } from './reportes/reportes.component';
import { ClientsComponent } from './clients/clients.component';

const routes: Routes = [
  {
    path: '',
    component: BaseComponent,
    children: [
      {
        path: 'clientes',
        component: ClientsComponent,
      },
      {
        path: 'cuentas',
        component: AccountsComponent,
      },
      {
        path: 'movimientos',
        component: MovementsComponent,
      },
      {
        path: 'reportes',
        component: ReportesComponent,
      },
      {
        path: '',
        redirectTo: 'clientes',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BaseRoutingModule {}

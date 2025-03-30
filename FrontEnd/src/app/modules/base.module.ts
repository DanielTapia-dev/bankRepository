import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseComponent } from './base.component';
import { RouterModule } from '@angular/router';
import { HeaderModule } from '../layout/header/header.module';
import { SidebarModule } from '../layout/sidebar/sidebar.module';
import { BaseRoutingModule } from './base-routing.module';

@NgModule({
  declarations: [BaseComponent],
  imports: [
    CommonModule,
    RouterModule,
    BaseRoutingModule,
    HeaderModule,
    SidebarModule,
  ],
})
export class BaseModule {}

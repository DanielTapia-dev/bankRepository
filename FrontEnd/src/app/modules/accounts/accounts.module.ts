import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountFormComponent } from './account-form/account-form.component';
import { AccountsComponent } from './accounts.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [AccountsComponent, AccountFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [AccountsComponent],
})
export class AccountsModule {}

import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/shared/interfaces/accounts';
import { AlertService } from 'src/app/shared/services/alert.service';
import { AccountService } from './services/cuenta.service';

@Component({
  selector: 'app-cuentas',
  templateUrl: './cuentas.component.html',
  styleUrls: ['./cuentas.component.scss'],
})
export class AccountsComponent implements OnInit {
  accounts: Account[] = [];

  constructor(
    private accountsService: AccountService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadCuentas();
  }

  loadCuentas() {
    this.accountsService.getAll().subscribe((resp) => {
      this.accounts = resp;
    });
  }

  editCuenta(account: Account) {
    console.log('Editar cliente:', account);
  }

  deleteCuenta(id: number) {
    this.accountsService.delete(id).subscribe(
      () => {
        this.alertService.success('Cuenta eliminada');
        this.loadCuentas();
      },
      (err) => {
        this.alertService.error('Error: ' + err.error.message);
      }
    );
  }
}

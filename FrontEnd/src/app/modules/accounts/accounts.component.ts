import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/shared/interfaces/accounts';
import { AlertService } from 'src/app/shared/services/alert.service';
import { AccountService } from './services/cuenta.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.scss'],
})
export class AccountsComponent implements OnInit {
  accounts: Account[] = [];
  showModal: boolean = false;
  accountSelected: Account | null = null;
  filteredAccounts: Account[] = [];

  constructor(
    private accountsService: AccountService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts() {
    this.accountsService.getAll().subscribe((resp) => {
      this.accounts = resp;
      this.filteredAccounts = resp;
    });
  }

  editCuenta(account: Account) {
    this.accountSelected = account;
    this.showModal = true;
  }

  deleteCuenta(id: number) {
    this.accountsService.delete(id).subscribe(
      () => {
        this.alertService.success('Cuenta eliminada');
        this.loadAccounts();
      },
      (err) => {
        this.alertService.error('Error: ' + err.error.message);
      }
    );
  }

  createAccount() {
    this.accountSelected = null;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  saveAccount(account: Account) {
    if (this.accountSelected) {
      this.accountsService.update(account.id!, account).subscribe({
        next: () => {
          this.alertService.success('Cuenta Modificado');
          this.loadAccounts();
          this.closeModal();
        },
        error: (err) => {
          this.alertService.error('Error: ' + err.error.message);
        },
      });
    } else {
      this.accountsService.create(account).subscribe({
        next: () => {
          this.alertService.success('Cuenta Agregada');
          this.loadAccounts();
          this.closeModal();
        },
        error: (err) => {
          this.alertService.error('Error: ' + err.error.message);
        },
      });
    }
  }

  applyFilter(term: string) {
    const lowerTerm = term.toLowerCase();
    this.filteredAccounts = this.accounts.filter((item) =>
      Object.values(item).some((value) =>
        value?.toString().toLowerCase().includes(lowerTerm)
      )
    );
  }
}

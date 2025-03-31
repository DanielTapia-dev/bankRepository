import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Account } from 'src/app/shared/interfaces/accounts';
import { Movement } from 'src/app/shared/interfaces/movements';
import { AccountService } from '../../accounts/services/cuenta.service';

@Component({
  selector: 'app-movement-form',
  templateUrl: './movement-form.component.html',
  styleUrls: ['./movement-form.component.scss'],
})
export class MovementFormComponent implements OnInit {
  @Input() movement: Movement | null = null;
  @Output() onSubmit = new EventEmitter<Movement>();
  @Output() onCancel = new EventEmitter<void>();

  form!: FormGroup;

  accounts: Account[] = [];
  accountSearch: string = '';
  filteredAccounts: Account[] = [];

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.loadAccounts();

    this.form = this.fb.group({
      fecha: [this.movement?.fecha || '', Validators.required],
      tipoMovimiento: [
        this.movement?.tipoMovimiento || '',
        Validators.required,
      ],
      valor: [
        this.movement?.valor || '',
        [Validators.required, Validators.min(0)],
      ],
      saldo: [
        { value: this.movement?.saldo, disabled: false },
        [Validators.required, Validators.min(0)],
      ],
      cuentaId: [this.movement?.cuentaId || '', Validators.required],
    });
  }

  submit(): void {
    if (this.form.valid) {
      const data: Movement = {
        ...this.movement,
        ...this.form.value,
      };
      this.onSubmit.emit(data);
    }
  }

  cancel(): void {
    this.onCancel.emit();
  }

  loadAccounts(): void {
    this.accountService.getAll().subscribe((resp) => {
      this.accounts = resp;
      this.filteredAccounts = this.accounts;
    });
  }

  filterAccounts(): void {
    const search = this.accountSearch.toLowerCase();
    this.filteredAccounts = this.accounts.filter((acc) =>
      `${acc.numeroCuenta} ${acc.clienteNombre}`.toLowerCase().includes(search)
    );
  }

  setSaldoInicial(): void {
    const selectedId = this.form.get('cuentaId')?.value;
    const selectedAccount = this.accounts.find(
      (account) => account.id === Number(selectedId)
    );
    if (selectedAccount) {
      this.form.patchValue({ saldo: selectedAccount.saldoInicial });
    }
  }
}

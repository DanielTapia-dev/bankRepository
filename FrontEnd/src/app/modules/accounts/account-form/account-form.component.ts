import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Account } from 'src/app/shared/interfaces/accounts';
import { ClientsService } from '../../clients/services/clientes.service';
import { Client } from 'src/app/shared/interfaces/client';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.scss'],
})
export class AccountFormComponent implements OnInit {
  @Input() account: Account | null = null;
  @Output() onSubmit = new EventEmitter<Account>();
  @Output() onCancel = new EventEmitter<void>();

  form!: FormGroup;

  clients: Client[] = [];
  clientSearch: string = '';
  filteredClients: Client[] = [];

  constructor(private fb: FormBuilder, private clientService: ClientsService) {}

  ngOnInit(): void {
    this.loadClients();
    this.form = this.fb.group({
      numeroCuenta: [this.account?.numeroCuenta || '', Validators.required],
      tipoCuenta: [this.account?.tipoCuenta || '', Validators.required],
      saldoInicial: [
        this.account?.saldoInicial || '',
        [Validators.required, Validators.min(0)],
      ],
      estado: [this.account?.estado ?? true, Validators.required],
      clienteId: [this.account?.clienteId || '', Validators.required],
    });
  }

  submit(): void {
    if (this.form.valid) {
      const data: Account = {
        ...this.account,
        ...this.form.value,
      };
      this.onSubmit.emit(data);
    }
  }

  cancel(): void {
    this.onCancel.emit();
  }

  loadClients() {
    this.clientService.getAll().subscribe((resp) => {
      this.clients = resp;
      this.filteredClients = this.clients;
    });
  }

  filterClients(): void {
    const search = this.clientSearch.toLowerCase();
    this.filteredClients = this.clients.filter((client) =>
      `${client.nombre} ${client.identificacion}`.toLowerCase().includes(search)
    );
  }
}

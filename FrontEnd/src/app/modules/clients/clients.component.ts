import { Component, OnInit } from '@angular/core';
import { Client } from 'src/app/shared/interfaces/client';
import { AlertService } from 'src/app/shared/services/alert.service';
import { ClientsService } from './services/clientes.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  clientSelected: Client | null = null;
  showModal: boolean = false;
  filteredClients: Client[] = [];

  constructor(
    private clientService: ClientsService,
    private alertService: AlertService
  ) {
    this.loadClients();
  }

  ngOnInit(): void {}

  loadClients() {
    this.clientService.getAll().subscribe((resp) => {
      this.clients = resp;
      this.filteredClients = resp;
    });
  }

  editClient(client: Client) {
    this.clientSelected = client;
    this.showModal = true;
  }

  deleteClient(id: number) {
    this.clientService.delete(id).subscribe({
      next: () => {
        this.alertService.success('Cliente eliminado');
        this.loadClients();
      },
      error: (err) => {
        this.alertService.error('Error: ' + err.error.message);
      },
    });
  }

  saveClient(client: Client) {
    if (this.clientSelected) {
      this.clientService.update(client.id!, client).subscribe({
        next: () => {
          this.alertService.success('Cliente Modificado');
          this.loadClients();
          this.closeModal();
        },
        error: (err) => {
          this.alertService.error('Error: ' + err.error.message);
        },
      });
    } else {
      this.clientService.create(client).subscribe({
        next: () => {
          this.alertService.success('Cliente Agregado');
          this.loadClients();
          this.closeModal();
        },
        error: (err) => {
          this.alertService.error('Error: ' + err.error.message);
        },
      });
    }
  }

  newClient(): void {
    this.clientSelected = null;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  applyFilter(term: string) {
    const lowerTerm = term.toLowerCase();
    this.filteredClients = this.clients.filter((item) =>
      Object.values(item).some((value) =>
        value?.toString().toLowerCase().includes(lowerTerm)
      )
    );
  }
}

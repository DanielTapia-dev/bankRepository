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
    });
  }

  editClient(client: Client) {
    this.clientSelected = client;
    this.showModal = true;
  }

  deleteClient(id: number) {
    this.clientService.delete(id).subscribe({
      next: () => {
        this.alertService.success('Client eliminado');
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
          this.alertService.success('Client Modificado');
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
          this.alertService.success('Client Agregado');
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
}

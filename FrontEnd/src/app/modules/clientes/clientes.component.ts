import { Component, OnInit } from '@angular/core';
import { ClientesService } from './services/clientes.service';
import { Cliente } from 'src/app/shared/interfaces/cliente';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.scss'],
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];

  constructor(
    private clientesService: ClientesService,
    private alertService: AlertService
  ) {
    this.loadClientes();
  }

  ngOnInit(): void {}

  loadClientes() {
    this.clientesService.getAll().subscribe((resp) => {
      this.clientes = resp;
    });
  }

  editCliente(cliente: Cliente) {
    console.log('Editar cliente:', cliente);
  }

  deleteCliente(id: number) {
    this.clientesService.delete(id).subscribe(() => {
      this.alertService.success('Cliente eliminado');
      this.loadClientes();
    });
  }
}

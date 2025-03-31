import { Component, OnInit } from '@angular/core';
import { Movement } from 'src/app/shared/interfaces/movements';
import { MovementService } from './services/movement.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-movements',
  templateUrl: './movements.component.html',
  styleUrls: ['./movements.component.scss'],
})
export class MovementsComponent implements OnInit {
  movimientos: Movement[] = [];
  showModal: boolean = false;
  movementSelected: Movement | null = null;

  constructor(
    private movementsService: MovementService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.loadMovements();
  }

  loadMovements() {
    this.movementsService.getAll().subscribe((resp) => {
      this.movimientos = resp;
    });
  }

  editMovimiento(movement: Movement) {
    this.movementSelected = movement;
    this.showModal = true;
  }

  deleteMovimiento(id: number) {
    this.movementsService.delete(id).subscribe(
      () => {
        this.alertService.success('Movimiento eliminada');
        this.loadMovements();
      },
      (err) => {
        this.alertService.error('Error: ' + err.error.message);
      }
    );
  }

  createMovement() {
    this.movementSelected = null;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  saveMovement(movement: Movement) {
    if (this.movementSelected) {
      this.movementsService.update(movement.id!, movement).subscribe({
        next: () => {
          this.alertService.success('Movimiento Modificado');
          this.loadMovements();
          this.closeModal();
        },
        error: (err) => {
          this.alertService.error('Error: ' + err.error.message);
        },
      });
    } else {
      this.movementsService.create(movement).subscribe({
        next: () => {
          this.alertService.success('Movimiento Agregada');
          this.loadMovements();
          this.closeModal();
        },
        error: (err) => {
          this.alertService.error('Error: ' + err.error.message);
        },
      });
    }
  }
}

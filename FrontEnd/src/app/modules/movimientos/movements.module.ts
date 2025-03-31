import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovementFormComponent } from './movement-form/movement-form.component';
import { MovementsComponent } from './movements.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [MovementsComponent, MovementFormComponent],
  imports: [CommonModule, SharedModule],
  exports: [MovementsComponent],
})
export class MovementsModule {}

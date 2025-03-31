import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesComponent } from './reportes.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ReportesComponent],
  imports: [CommonModule, FormsModule],
  exports: [ReportesComponent],
})
export class ReportesModule {}

import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Report } from 'src/app/shared/interfaces/reports';
import { ReportService } from 'src/app/shared/services/report.service';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.scss'],
})
export class ReportesComponent {
  reportes: Report[] = [];
  clienteId: number = 0;
  fechaInicio!: string;
  fechaFin!: string;

  constructor(private http: HttpClient, private reportService: ReportService) {}

  loadReport() {
    this.reportService
      .getReport(this.clienteId, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (data) => (this.reportes = data),
        error: (err) => console.error('Error al cargar reporte:', err),
      });
  }

  downloadPdf() {
    this.reportService
      .getReportPdf(this.clienteId, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response) => {
          const byteCharacters = atob(response.base64);
          const byteNumbers = new Array(byteCharacters.length)
            .fill(0)
            .map((_, i) => byteCharacters.charCodeAt(i));
          const byteArray = new Uint8Array(byteNumbers);
          const blob = new Blob([byteArray], { type: 'application/pdf' });
          const link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.download = 'reporte.pdf';
          link.click();
        },
        error: (err) => console.error('Error al cargar reporte:', err),
      });
  }
}

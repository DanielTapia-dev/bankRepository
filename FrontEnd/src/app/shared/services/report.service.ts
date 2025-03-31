import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface ReportDTO {
  numeroCuenta: string;
  saldoInicial: number;
  totalCreditos: number;
  totalDebitos: number;
  clienteNombre?: string;
}

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  private baseUrl = `${environment.apiUrl}/movimientos`;

  constructor(private http: HttpClient) {}

  getReport(
    clienteId: number,
    fechaInicio: string,
    fechaFin: string
  ): Observable<ReportDTO[]> {
    const params = new HttpParams()
      .set('clienteId', clienteId)
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);
    return this.http.get<ReportDTO[]>(`${this.baseUrl}/reportes/json`, {
      params,
    });
  }

  getReportPdf(
    clienteId: number,
    fechaInicio: string,
    fechaFin: string
  ): Observable<{ base64: string }> {
    const params = new HttpParams()
      .set('clienteId', clienteId.toString())
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);
    return this.http.get<{ base64: string }>(`${this.baseUrl}/reportes/pdf`, {
      params,
    });
  }
}

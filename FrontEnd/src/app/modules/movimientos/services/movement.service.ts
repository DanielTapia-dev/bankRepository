import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movement } from 'src/app/shared/interfaces/movements';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class MovementService {
  private apiUrl = `${environment.apiUrl}/movimientos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Movement[]> {
    return this.http.get<Movement[]>(this.apiUrl);
  }

  getById(id: number): Observable<Movement> {
    return this.http.get<Movement>(`${this.apiUrl}/${id}`);
  }

  create(cliente: Movement): Observable<Movement> {
    return this.http.post<Movement>(this.apiUrl, cliente);
  }

  update(id: number, cliente: Movement): Observable<Movement> {
    return this.http.put<Movement>(`${this.apiUrl}/${id}`, cliente);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent {
  @Input() colLabels: string[] = [];
  @Input() colNames: string[] = [];
  @Input() data: any[] = [];

  @Output() onEdit = new EventEmitter<any>();
  @Output() onDelete = new EventEmitter<number>();

  pageSize = 10;
  currentPage = 1;

  constructor() {
    if (!this.colNames) {
      this.colNames = this.colLabels;
    }
  }

  paginatedData() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.data.slice(start, start + this.pageSize);
  }

  totalPages() {
    return Math.ceil(this.data.length / this.pageSize);
  }

  nextPage() {
    if (this.currentPage < this.totalPages()) this.currentPage++;
  }

  prevPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  edit(item: any) {
    this.onEdit.emit(item);
  }

  delete(id: number) {
    this.onDelete.emit(id);
  }

  isDate(value: any): boolean {
    return !isNaN(Date.parse(value));
  }
}

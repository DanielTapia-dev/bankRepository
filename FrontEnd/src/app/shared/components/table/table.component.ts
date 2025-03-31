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

  constructor() {
    if (!this.colNames) {
      this.colNames = this.colLabels;
    }
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

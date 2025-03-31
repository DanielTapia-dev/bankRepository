import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Client } from 'src/app/shared/interfaces/client';

@Component({
  selector: 'app-client-form',
  templateUrl: './client-form.component.html',
  styleUrls: ['./client-form.component.scss'],
})
export class ClientFormComponent implements OnInit {
  @Input() client: Client | null = null;
  @Output() onSubmit = new EventEmitter<Client>();
  @Output() onCancel = new EventEmitter<void>();

  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: [this.client?.nombre || '', Validators.required],
      genero: [this.client?.genero || '', Validators.required],
      edad: [this.client?.edad || '', [Validators.required, Validators.min(1)]],
      identificacion: [this.client?.identificacion || '', Validators.required],
      direccion: [this.client?.direccion || '', Validators.required],
      telefono: [
        this.client?.telefono || '',
        [Validators.required, Validators.pattern(/^[0-9]{7,15}$/)],
      ],
      contrasena: ['', this.client ? [] : [Validators.required]],
      estado: [this.client?.estado ?? true, Validators.required],
    });
  }

  submit(): void {
    if (this.form.valid) {
      let formData: any = {
        ...this.client,
        ...this.form.value,
      };

      if (!formData.contrasena || formData.contrasena.trim() === '') {
        delete formData.contrasena;
      }

      this.onSubmit.emit(formData);
    }
  }

  cancel(): void {
    this.onCancel.emit();
  }
}

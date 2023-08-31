import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ExternalEmployeeFormService, ExternalEmployeeFormGroup } from './external-employee-form.service';
import { IExternalEmployee } from '../external-employee.model';
import { ExternalEmployeeService } from '../service/external-employee.service';

@Component({
  selector: 'jhi-external-employee-update',
  templateUrl: './external-employee-update.component.html',
})
export class ExternalEmployeeUpdateComponent implements OnInit {
  isSaving = false;
  externalEmployee: IExternalEmployee | null = null;

  editForm: ExternalEmployeeFormGroup = this.externalEmployeeFormService.createExternalEmployeeFormGroup();

  constructor(
    protected externalEmployeeService: ExternalEmployeeService,
    protected externalEmployeeFormService: ExternalEmployeeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ externalEmployee }) => {
      this.externalEmployee = externalEmployee;
      if (externalEmployee) {
        this.updateForm(externalEmployee);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const externalEmployee = this.externalEmployeeFormService.getExternalEmployee(this.editForm);
    if (externalEmployee.id !== null) {
      this.subscribeToSaveResponse(this.externalEmployeeService.update(externalEmployee));
    } else {
      this.subscribeToSaveResponse(this.externalEmployeeService.create(externalEmployee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExternalEmployee>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(externalEmployee: IExternalEmployee): void {
    this.externalEmployee = externalEmployee;
    this.externalEmployeeFormService.resetForm(this.editForm, externalEmployee);
  }
}

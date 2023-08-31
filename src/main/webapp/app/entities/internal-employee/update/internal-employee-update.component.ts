import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { InternalEmployeeFormService, InternalEmployeeFormGroup } from './internal-employee-form.service';
import { IInternalEmployee } from '../internal-employee.model';
import { InternalEmployeeService } from '../service/internal-employee.service';

@Component({
  selector: 'jhi-internal-employee-update',
  templateUrl: './internal-employee-update.component.html',
})
export class InternalEmployeeUpdateComponent implements OnInit {
  isSaving = false;
  internalEmployee: IInternalEmployee | null = null;

  editForm: InternalEmployeeFormGroup = this.internalEmployeeFormService.createInternalEmployeeFormGroup();

  constructor(
    protected internalEmployeeService: InternalEmployeeService,
    protected internalEmployeeFormService: InternalEmployeeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ internalEmployee }) => {
      this.internalEmployee = internalEmployee;
      if (internalEmployee) {
        this.updateForm(internalEmployee);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const internalEmployee = this.internalEmployeeFormService.getInternalEmployee(this.editForm);
    if (internalEmployee.id !== null) {
      this.subscribeToSaveResponse(this.internalEmployeeService.update(internalEmployee));
    } else {
      this.subscribeToSaveResponse(this.internalEmployeeService.create(internalEmployee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInternalEmployee>>): void {
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

  protected updateForm(internalEmployee: IInternalEmployee): void {
    this.internalEmployee = internalEmployee;
    this.internalEmployeeFormService.resetForm(this.editForm, internalEmployee);
  }
}

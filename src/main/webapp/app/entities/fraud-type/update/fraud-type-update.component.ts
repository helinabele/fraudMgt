import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FraudTypeFormService, FraudTypeFormGroup } from './fraud-type-form.service';
import { IFraudType } from '../fraud-type.model';
import { FraudTypeService } from '../service/fraud-type.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-fraud-type-update',
  templateUrl: './fraud-type-update.component.html',
})
export class FraudTypeUpdateComponent implements OnInit {
  isSaving = false;
  fraudType: IFraudType | null = null;

  editForm: FraudTypeFormGroup = this.fraudTypeFormService.createFraudTypeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fraudTypeService: FraudTypeService,
    protected fraudTypeFormService: FraudTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fraudType }) => {
      this.fraudType = fraudType;
      if (fraudType) {
        this.updateForm(fraudType);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('fraudMgtApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fraudType = this.fraudTypeFormService.getFraudType(this.editForm);
    if (fraudType.id !== null) {
      this.subscribeToSaveResponse(this.fraudTypeService.update(fraudType));
    } else {
      this.subscribeToSaveResponse(this.fraudTypeService.create(fraudType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFraudType>>): void {
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

  protected updateForm(fraudType: IFraudType): void {
    this.fraudType = fraudType;
    this.fraudTypeFormService.resetForm(this.editForm, fraudType);
  }
}

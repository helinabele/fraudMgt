import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { WhistleBlowerReportFormService, WhistleBlowerReportFormGroup } from './whistle-blower-report-form.service';
import { IWhistleBlowerReport } from '../whistle-blower-report.model';
import { WhistleBlowerReportService } from '../service/whistle-blower-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { Gender } from 'app/entities/enumerations/gender.model';

@Component({
  selector: 'jhi-whistle-blower-report-update',
  templateUrl: './whistle-blower-report-update.component.html',
})
export class WhistleBlowerReportUpdateComponent implements OnInit {
  isSaving = false;
  whistleBlowerReport: IWhistleBlowerReport | null = null;
  genderValues = Object.keys(Gender);

  editForm: WhistleBlowerReportFormGroup = this.whistleBlowerReportFormService.createWhistleBlowerReportFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected whistleBlowerReportService: WhistleBlowerReportService,
    protected whistleBlowerReportFormService: WhistleBlowerReportFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ whistleBlowerReport }) => {
      this.whistleBlowerReport = whistleBlowerReport;
      if (whistleBlowerReport) {
        this.updateForm(whistleBlowerReport);
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
    const whistleBlowerReport = this.whistleBlowerReportFormService.getWhistleBlowerReport(this.editForm);
    if (whistleBlowerReport.id !== null) {
      this.subscribeToSaveResponse(this.whistleBlowerReportService.update(whistleBlowerReport));
    } else {
      this.subscribeToSaveResponse(this.whistleBlowerReportService.create(whistleBlowerReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWhistleBlowerReport>>): void {
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

  protected updateForm(whistleBlowerReport: IWhistleBlowerReport): void {
    this.whistleBlowerReport = whistleBlowerReport;
    this.whistleBlowerReportFormService.resetForm(this.editForm, whistleBlowerReport);
  }
}

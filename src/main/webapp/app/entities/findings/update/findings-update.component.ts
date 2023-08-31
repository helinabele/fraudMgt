import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FindingsFormService, FindingsFormGroup } from './findings-form.service';
import { IFindings } from '../findings.model';
import { FindingsService } from '../service/findings.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IFraudInvestigationReport } from 'app/entities/fraud-investigation-report/fraud-investigation-report.model';
import { FraudInvestigationReportService } from 'app/entities/fraud-investigation-report/service/fraud-investigation-report.service';

@Component({
  selector: 'jhi-findings-update',
  templateUrl: './findings-update.component.html',
})
export class FindingsUpdateComponent implements OnInit {
  isSaving = false;
  findings: IFindings | null = null;

  fraudInvestigationReportsSharedCollection: IFraudInvestigationReport[] = [];

  editForm: FindingsFormGroup = this.findingsFormService.createFindingsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected findingsService: FindingsService,
    protected findingsFormService: FindingsFormService,
    protected fraudInvestigationReportService: FraudInvestigationReportService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFraudInvestigationReport = (o1: IFraudInvestigationReport | null, o2: IFraudInvestigationReport | null): boolean =>
    this.fraudInvestigationReportService.compareFraudInvestigationReport(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ findings }) => {
      this.findings = findings;
      if (findings) {
        this.updateForm(findings);
      }

      this.loadRelationshipsOptions();
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
    const findings = this.findingsFormService.getFindings(this.editForm);
    if (findings.id !== null) {
      this.subscribeToSaveResponse(this.findingsService.update(findings));
    } else {
      this.subscribeToSaveResponse(this.findingsService.create(findings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFindings>>): void {
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

  protected updateForm(findings: IFindings): void {
    this.findings = findings;
    this.findingsFormService.resetForm(this.editForm, findings);

    this.fraudInvestigationReportsSharedCollection =
      this.fraudInvestigationReportService.addFraudInvestigationReportToCollectionIfMissing<IFraudInvestigationReport>(
        this.fraudInvestigationReportsSharedCollection,
        findings.fraudInvestigationReport
      );
  }

  protected loadRelationshipsOptions(): void {
    this.fraudInvestigationReportService
      .query()
      .pipe(map((res: HttpResponse<IFraudInvestigationReport[]>) => res.body ?? []))
      .pipe(
        map((fraudInvestigationReports: IFraudInvestigationReport[]) =>
          this.fraudInvestigationReportService.addFraudInvestigationReportToCollectionIfMissing<IFraudInvestigationReport>(
            fraudInvestigationReports,
            this.findings?.fraudInvestigationReport
          )
        )
      )
      .subscribe(
        (fraudInvestigationReports: IFraudInvestigationReport[]) =>
          (this.fraudInvestigationReportsSharedCollection = fraudInvestigationReports)
      );
  }
}

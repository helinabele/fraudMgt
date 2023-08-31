import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFraudInvestigationReport } from '../fraud-investigation-report.model';
import { FraudInvestigationReportService } from '../service/fraud-investigation-report.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fraud-investigation-report-delete-dialog.component.html',
})
export class FraudInvestigationReportDeleteDialogComponent {
  fraudInvestigationReport?: IFraudInvestigationReport;

  constructor(protected fraudInvestigationReportService: FraudInvestigationReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.fraudInvestigationReportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWhistleBlowerReport } from '../whistle-blower-report.model';
import { WhistleBlowerReportService } from '../service/whistle-blower-report.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './whistle-blower-report-delete-dialog.component.html',
})
export class WhistleBlowerReportDeleteDialogComponent {
  whistleBlowerReport?: IWhistleBlowerReport;

  constructor(protected whistleBlowerReportService: WhistleBlowerReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.whistleBlowerReportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

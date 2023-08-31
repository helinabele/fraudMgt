import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFraudKnowledgeManagement } from '../fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from '../service/fraud-knowledge-management.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fraud-knowledge-management-delete-dialog.component.html',
})
export class FraudKnowledgeManagementDeleteDialogComponent {
  fraudKnowledgeManagement?: IFraudKnowledgeManagement;

  constructor(protected fraudKnowledgeManagementService: FraudKnowledgeManagementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.fraudKnowledgeManagementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

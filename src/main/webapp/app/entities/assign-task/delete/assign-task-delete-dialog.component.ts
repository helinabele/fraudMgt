import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssignTask } from '../assign-task.model';
import { AssignTaskService } from '../service/assign-task.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './assign-task-delete-dialog.component.html',
})
export class AssignTaskDeleteDialogComponent {
  assignTask?: IAssignTask;

  constructor(protected assignTaskService: AssignTaskService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.assignTaskService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

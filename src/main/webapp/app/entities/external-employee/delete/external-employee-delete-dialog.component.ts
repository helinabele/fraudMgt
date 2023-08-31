import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExternalEmployee } from '../external-employee.model';
import { ExternalEmployeeService } from '../service/external-employee.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './external-employee-delete-dialog.component.html',
})
export class ExternalEmployeeDeleteDialogComponent {
  externalEmployee?: IExternalEmployee;

  constructor(protected externalEmployeeService: ExternalEmployeeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.externalEmployeeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

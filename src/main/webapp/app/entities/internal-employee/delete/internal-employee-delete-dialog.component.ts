import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInternalEmployee } from '../internal-employee.model';
import { InternalEmployeeService } from '../service/internal-employee.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './internal-employee-delete-dialog.component.html',
})
export class InternalEmployeeDeleteDialogComponent {
  internalEmployee?: IInternalEmployee;

  constructor(protected internalEmployeeService: InternalEmployeeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.internalEmployeeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

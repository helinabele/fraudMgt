import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IManagerial } from '../managerial.model';
import { ManagerialService } from '../service/managerial.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './managerial-delete-dialog.component.html',
})
export class ManagerialDeleteDialogComponent {
  managerial?: IManagerial;

  constructor(protected managerialService: ManagerialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.managerialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

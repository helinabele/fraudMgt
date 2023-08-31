import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamLead } from '../team-lead.model';
import { TeamLeadService } from '../service/team-lead.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './team-lead-delete-dialog.component.html',
})
export class TeamLeadDeleteDialogComponent {
  teamLead?: ITeamLead;

  constructor(protected teamLeadService: TeamLeadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.teamLeadService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

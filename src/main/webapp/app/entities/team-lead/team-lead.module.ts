import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TeamLeadComponent } from './list/team-lead.component';
import { TeamLeadDetailComponent } from './detail/team-lead-detail.component';
import { TeamLeadUpdateComponent } from './update/team-lead-update.component';
import { TeamLeadDeleteDialogComponent } from './delete/team-lead-delete-dialog.component';
import { TeamLeadRoutingModule } from './route/team-lead-routing.module';

@NgModule({
  imports: [SharedModule, TeamLeadRoutingModule],
  declarations: [TeamLeadComponent, TeamLeadDetailComponent, TeamLeadUpdateComponent, TeamLeadDeleteDialogComponent],
})
export class TeamLeadModule {}

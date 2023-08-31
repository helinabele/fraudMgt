import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TeamLeadComponent } from '../list/team-lead.component';
import { TeamLeadDetailComponent } from '../detail/team-lead-detail.component';
import { TeamLeadUpdateComponent } from '../update/team-lead-update.component';
import { TeamLeadRoutingResolveService } from './team-lead-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const teamLeadRoute: Routes = [
  {
    path: '',
    component: TeamLeadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeamLeadDetailComponent,
    resolve: {
      teamLead: TeamLeadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeamLeadUpdateComponent,
    resolve: {
      teamLead: TeamLeadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeamLeadUpdateComponent,
    resolve: {
      teamLead: TeamLeadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(teamLeadRoute)],
  exports: [RouterModule],
})
export class TeamLeadRoutingModule {}

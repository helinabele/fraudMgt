import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FindingsComponent } from '../list/findings.component';
import { FindingsDetailComponent } from '../detail/findings-detail.component';
import { FindingsUpdateComponent } from '../update/findings-update.component';
import { FindingsRoutingResolveService } from './findings-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const findingsRoute: Routes = [
  {
    path: '',
    component: FindingsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FindingsDetailComponent,
    resolve: {
      findings: FindingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FindingsUpdateComponent,
    resolve: {
      findings: FindingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FindingsUpdateComponent,
    resolve: {
      findings: FindingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(findingsRoute)],
  exports: [RouterModule],
})
export class FindingsRoutingModule {}

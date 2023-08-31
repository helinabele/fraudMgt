import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ManagerialComponent } from '../list/managerial.component';
import { ManagerialDetailComponent } from '../detail/managerial-detail.component';
import { ManagerialUpdateComponent } from '../update/managerial-update.component';
import { ManagerialRoutingResolveService } from './managerial-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const managerialRoute: Routes = [
  {
    path: '',
    component: ManagerialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ManagerialDetailComponent,
    resolve: {
      managerial: ManagerialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ManagerialUpdateComponent,
    resolve: {
      managerial: ManagerialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ManagerialUpdateComponent,
    resolve: {
      managerial: ManagerialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(managerialRoute)],
  exports: [RouterModule],
})
export class ManagerialRoutingModule {}

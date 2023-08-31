import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExternalEmployeeComponent } from '../list/external-employee.component';
import { ExternalEmployeeDetailComponent } from '../detail/external-employee-detail.component';
import { ExternalEmployeeUpdateComponent } from '../update/external-employee-update.component';
import { ExternalEmployeeRoutingResolveService } from './external-employee-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const externalEmployeeRoute: Routes = [
  {
    path: '',
    component: ExternalEmployeeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExternalEmployeeDetailComponent,
    resolve: {
      externalEmployee: ExternalEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExternalEmployeeUpdateComponent,
    resolve: {
      externalEmployee: ExternalEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExternalEmployeeUpdateComponent,
    resolve: {
      externalEmployee: ExternalEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(externalEmployeeRoute)],
  exports: [RouterModule],
})
export class ExternalEmployeeRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InternalEmployeeComponent } from '../list/internal-employee.component';
import { InternalEmployeeDetailComponent } from '../detail/internal-employee-detail.component';
import { InternalEmployeeUpdateComponent } from '../update/internal-employee-update.component';
import { InternalEmployeeRoutingResolveService } from './internal-employee-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const internalEmployeeRoute: Routes = [
  {
    path: '',
    component: InternalEmployeeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InternalEmployeeDetailComponent,
    resolve: {
      internalEmployee: InternalEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InternalEmployeeUpdateComponent,
    resolve: {
      internalEmployee: InternalEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InternalEmployeeUpdateComponent,
    resolve: {
      internalEmployee: InternalEmployeeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(internalEmployeeRoute)],
  exports: [RouterModule],
})
export class InternalEmployeeRoutingModule {}

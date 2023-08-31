import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BankServiceComponent } from '../list/bank-service.component';
import { BankServiceDetailComponent } from '../detail/bank-service-detail.component';
import { BankServiceUpdateComponent } from '../update/bank-service-update.component';
import { BankServiceRoutingResolveService } from './bank-service-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const bankServiceRoute: Routes = [
  {
    path: '',
    component: BankServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankServiceDetailComponent,
    resolve: {
      bankService: BankServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankServiceUpdateComponent,
    resolve: {
      bankService: BankServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankServiceUpdateComponent,
    resolve: {
      bankService: BankServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bankServiceRoute)],
  exports: [RouterModule],
})
export class BankServiceRoutingModule {}

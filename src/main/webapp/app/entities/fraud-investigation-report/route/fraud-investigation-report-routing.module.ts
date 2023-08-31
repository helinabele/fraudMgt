import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FraudInvestigationReportComponent } from '../list/fraud-investigation-report.component';
import { FraudInvestigationReportDetailComponent } from '../detail/fraud-investigation-report-detail.component';
import { FraudInvestigationReportUpdateComponent } from '../update/fraud-investigation-report-update.component';
import { FraudInvestigationReportRoutingResolveService } from './fraud-investigation-report-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fraudInvestigationReportRoute: Routes = [
  {
    path: '',
    component: FraudInvestigationReportComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FraudInvestigationReportDetailComponent,
    resolve: {
      fraudInvestigationReport: FraudInvestigationReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FraudInvestigationReportUpdateComponent,
    resolve: {
      fraudInvestigationReport: FraudInvestigationReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FraudInvestigationReportUpdateComponent,
    resolve: {
      fraudInvestigationReport: FraudInvestigationReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fraudInvestigationReportRoute)],
  exports: [RouterModule],
})
export class FraudInvestigationReportRoutingModule {}

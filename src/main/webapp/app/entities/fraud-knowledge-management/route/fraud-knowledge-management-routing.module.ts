import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FraudKnowledgeManagementComponent } from '../list/fraud-knowledge-management.component';
import { FraudKnowledgeManagementDetailComponent } from '../detail/fraud-knowledge-management-detail.component';
import { FraudKnowledgeManagementUpdateComponent } from '../update/fraud-knowledge-management-update.component';
import { FraudKnowledgeManagementRoutingResolveService } from './fraud-knowledge-management-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fraudKnowledgeManagementRoute: Routes = [
  {
    path: '',
    component: FraudKnowledgeManagementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FraudKnowledgeManagementDetailComponent,
    resolve: {
      fraudKnowledgeManagement: FraudKnowledgeManagementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FraudKnowledgeManagementUpdateComponent,
    resolve: {
      fraudKnowledgeManagement: FraudKnowledgeManagementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FraudKnowledgeManagementUpdateComponent,
    resolve: {
      fraudKnowledgeManagement: FraudKnowledgeManagementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fraudKnowledgeManagementRoute)],
  exports: [RouterModule],
})
export class FraudKnowledgeManagementRoutingModule {}

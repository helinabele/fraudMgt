import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FraudKnowledgeManagementComponent } from './list/fraud-knowledge-management.component';
import { FraudKnowledgeManagementDetailComponent } from './detail/fraud-knowledge-management-detail.component';
import { FraudKnowledgeManagementUpdateComponent } from './update/fraud-knowledge-management-update.component';
import { FraudKnowledgeManagementDeleteDialogComponent } from './delete/fraud-knowledge-management-delete-dialog.component';
import { FraudKnowledgeManagementRoutingModule } from './route/fraud-knowledge-management-routing.module';

@NgModule({
  imports: [SharedModule, FraudKnowledgeManagementRoutingModule],
  declarations: [
    FraudKnowledgeManagementComponent,
    FraudKnowledgeManagementDetailComponent,
    FraudKnowledgeManagementUpdateComponent,
    FraudKnowledgeManagementDeleteDialogComponent,
  ],
})
export class FraudKnowledgeManagementModule {}

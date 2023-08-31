import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExternalEmployeeComponent } from './list/external-employee.component';
import { ExternalEmployeeDetailComponent } from './detail/external-employee-detail.component';
import { ExternalEmployeeUpdateComponent } from './update/external-employee-update.component';
import { ExternalEmployeeDeleteDialogComponent } from './delete/external-employee-delete-dialog.component';
import { ExternalEmployeeRoutingModule } from './route/external-employee-routing.module';

@NgModule({
  imports: [SharedModule, ExternalEmployeeRoutingModule],
  declarations: [
    ExternalEmployeeComponent,
    ExternalEmployeeDetailComponent,
    ExternalEmployeeUpdateComponent,
    ExternalEmployeeDeleteDialogComponent,
  ],
})
export class ExternalEmployeeModule {}

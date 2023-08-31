import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InternalEmployeeComponent } from './list/internal-employee.component';
import { InternalEmployeeDetailComponent } from './detail/internal-employee-detail.component';
import { InternalEmployeeUpdateComponent } from './update/internal-employee-update.component';
import { InternalEmployeeDeleteDialogComponent } from './delete/internal-employee-delete-dialog.component';
import { InternalEmployeeRoutingModule } from './route/internal-employee-routing.module';

@NgModule({
  imports: [SharedModule, InternalEmployeeRoutingModule],
  declarations: [
    InternalEmployeeComponent,
    InternalEmployeeDetailComponent,
    InternalEmployeeUpdateComponent,
    InternalEmployeeDeleteDialogComponent,
  ],
})
export class InternalEmployeeModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FindingsComponent } from './list/findings.component';
import { FindingsDetailComponent } from './detail/findings-detail.component';
import { FindingsUpdateComponent } from './update/findings-update.component';
import { FindingsDeleteDialogComponent } from './delete/findings-delete-dialog.component';
import { FindingsRoutingModule } from './route/findings-routing.module';

@NgModule({
  imports: [SharedModule, FindingsRoutingModule],
  declarations: [FindingsComponent, FindingsDetailComponent, FindingsUpdateComponent, FindingsDeleteDialogComponent],
})
export class FindingsModule {}

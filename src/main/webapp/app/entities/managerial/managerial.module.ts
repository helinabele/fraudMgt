import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ManagerialComponent } from './list/managerial.component';
import { ManagerialDetailComponent } from './detail/managerial-detail.component';
import { ManagerialUpdateComponent } from './update/managerial-update.component';
import { ManagerialDeleteDialogComponent } from './delete/managerial-delete-dialog.component';
import { ManagerialRoutingModule } from './route/managerial-routing.module';

@NgModule({
  imports: [SharedModule, ManagerialRoutingModule],
  declarations: [ManagerialComponent, ManagerialDetailComponent, ManagerialUpdateComponent, ManagerialDeleteDialogComponent],
})
export class ManagerialModule {}

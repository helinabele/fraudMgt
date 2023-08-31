import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BankServiceComponent } from './list/bank-service.component';
import { BankServiceDetailComponent } from './detail/bank-service-detail.component';
import { BankServiceUpdateComponent } from './update/bank-service-update.component';
import { BankServiceDeleteDialogComponent } from './delete/bank-service-delete-dialog.component';
import { BankServiceRoutingModule } from './route/bank-service-routing.module';

@NgModule({
  imports: [SharedModule, BankServiceRoutingModule],
  declarations: [BankServiceComponent, BankServiceDetailComponent, BankServiceUpdateComponent, BankServiceDeleteDialogComponent],
})
export class BankServiceModule {}

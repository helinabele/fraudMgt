import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { reportRoute } from './report.route';

const REPORT_ROUTES = reportRoute;

@NgModule({
  imports: [RouterModule.forChild(REPORT_ROUTES)],
  declarations: [
  ],
  entryComponents:[
  ]
})
export class ReportRoutingModule { }
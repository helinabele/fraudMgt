

import { Routes } from '@angular/router';
import { FraudKnowledgeManagementReportComponent } from './fraud-knowledge-management-report/fraud-knowledge-management-report.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WeeklyFraudReportComponent } from './weekly-fraud-report/weekly-fraud-report.component';
import { MonthlyFraudAttemptComponent } from './monthly-fraud-attempt/monthly-fraud-attempt.component';
import { QuarterlyInvestigationPerformanceReportComponent } from './quarterly-investigation-performance-report/quarterly-investigation-performance-report.component';
import { OutstandingQuarterlySummaryReportComponent } from './outstanding-quarterly-summary-report/outstanding-quarterly-summary-report.component';
import { WhistleBlowerQuarterlyReportComponent } from './whistle-blower-quarterly-report/whistle-blower-quarterly-report.component';

export const reportRoute: Routes = [
  {
    path: 'fraud-knowledge-management-report',
    component: FraudKnowledgeManagementReportComponent,
    data: {
      pageTitle: 'report.fraudKnowledgeManagementReport'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'weekly-fraud-report',
    component: WeeklyFraudReportComponent,
    data: {
      pageTitle: 'report.weeklyFraudReport'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'monthly-fraud-attempt',
    component: MonthlyFraudAttemptComponent,
    data:{
      pageTitle: 'report.monthlyFraudAttempt'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'quarterly-investigation-performance-report',
    component: QuarterlyInvestigationPerformanceReportComponent,
    data:{
      pageTitle: 'report.quarterlyInvestigationPerformanceReport'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'outstanding-quarterly-summary-report',
    component: OutstandingQuarterlySummaryReportComponent,
    data: {
      pageTitle: 'report.outstandingQuarterlySummaryReport'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'whistle-blower-quarterly-report',
    component: WhistleBlowerQuarterlyReportComponent,
    data: {
      pageTitle:'report.whistleBlowerQuarterlyReport'
    },
    canActivate: [UserRouteAccessService]
  }
];
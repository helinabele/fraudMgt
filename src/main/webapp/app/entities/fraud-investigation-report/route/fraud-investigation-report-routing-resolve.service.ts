import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFraudInvestigationReport } from '../fraud-investigation-report.model';
import { FraudInvestigationReportService } from '../service/fraud-investigation-report.service';

@Injectable({ providedIn: 'root' })
export class FraudInvestigationReportRoutingResolveService implements Resolve<IFraudInvestigationReport | null> {
  constructor(protected service: FraudInvestigationReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFraudInvestigationReport | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fraudInvestigationReport: HttpResponse<IFraudInvestigationReport>) => {
          if (fraudInvestigationReport.body) {
            return of(fraudInvestigationReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

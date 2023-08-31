import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWhistleBlowerReport } from '../whistle-blower-report.model';
import { WhistleBlowerReportService } from '../service/whistle-blower-report.service';

@Injectable({ providedIn: 'root' })
export class WhistleBlowerReportRoutingResolveService implements Resolve<IWhistleBlowerReport | null> {
  constructor(protected service: WhistleBlowerReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWhistleBlowerReport | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((whistleBlowerReport: HttpResponse<IWhistleBlowerReport>) => {
          if (whistleBlowerReport.body) {
            return of(whistleBlowerReport.body);
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

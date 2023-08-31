import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFindings } from '../findings.model';
import { FindingsService } from '../service/findings.service';

@Injectable({ providedIn: 'root' })
export class FindingsRoutingResolveService implements Resolve<IFindings | null> {
  constructor(protected service: FindingsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFindings | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((findings: HttpResponse<IFindings>) => {
          if (findings.body) {
            return of(findings.body);
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

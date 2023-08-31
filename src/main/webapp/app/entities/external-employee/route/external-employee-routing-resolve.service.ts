import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExternalEmployee } from '../external-employee.model';
import { ExternalEmployeeService } from '../service/external-employee.service';

@Injectable({ providedIn: 'root' })
export class ExternalEmployeeRoutingResolveService implements Resolve<IExternalEmployee | null> {
  constructor(protected service: ExternalEmployeeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExternalEmployee | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((externalEmployee: HttpResponse<IExternalEmployee>) => {
          if (externalEmployee.body) {
            return of(externalEmployee.body);
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

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInternalEmployee } from '../internal-employee.model';
import { InternalEmployeeService } from '../service/internal-employee.service';

@Injectable({ providedIn: 'root' })
export class InternalEmployeeRoutingResolveService implements Resolve<IInternalEmployee | null> {
  constructor(protected service: InternalEmployeeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInternalEmployee | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((internalEmployee: HttpResponse<IInternalEmployee>) => {
          if (internalEmployee.body) {
            return of(internalEmployee.body);
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

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IManagerial } from '../managerial.model';
import { ManagerialService } from '../service/managerial.service';

@Injectable({ providedIn: 'root' })
export class ManagerialRoutingResolveService implements Resolve<IManagerial | null> {
  constructor(protected service: ManagerialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IManagerial | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((managerial: HttpResponse<IManagerial>) => {
          if (managerial.body) {
            return of(managerial.body);
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

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBankService } from '../bank-service.model';
import { BankServiceService } from '../service/bank-service.service';

@Injectable({ providedIn: 'root' })
export class BankServiceRoutingResolveService implements Resolve<IBankService | null> {
  constructor(protected service: BankServiceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankService | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bankService: HttpResponse<IBankService>) => {
          if (bankService.body) {
            return of(bankService.body);
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

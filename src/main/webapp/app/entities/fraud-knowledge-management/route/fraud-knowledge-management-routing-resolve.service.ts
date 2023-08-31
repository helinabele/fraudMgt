import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFraudKnowledgeManagement } from '../fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from '../service/fraud-knowledge-management.service';

@Injectable({ providedIn: 'root' })
export class FraudKnowledgeManagementRoutingResolveService implements Resolve<IFraudKnowledgeManagement | null> {
  constructor(protected service: FraudKnowledgeManagementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFraudKnowledgeManagement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fraudKnowledgeManagement: HttpResponse<IFraudKnowledgeManagement>) => {
          if (fraudKnowledgeManagement.body) {
            return of(fraudKnowledgeManagement.body);
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

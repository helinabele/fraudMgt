import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssignTask } from '../assign-task.model';
import { AssignTaskService } from '../service/assign-task.service';

@Injectable({ providedIn: 'root' })
export class AssignTaskRoutingResolveService implements Resolve<IAssignTask | null> {
  constructor(protected service: AssignTaskService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssignTask | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((assignTask: HttpResponse<IAssignTask>) => {
          if (assignTask.body) {
            return of(assignTask.body);
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

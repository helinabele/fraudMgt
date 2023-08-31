import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITeamLead } from '../team-lead.model';
import { TeamLeadService } from '../service/team-lead.service';

@Injectable({ providedIn: 'root' })
export class TeamLeadRoutingResolveService implements Resolve<ITeamLead | null> {
  constructor(protected service: TeamLeadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeamLead | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((teamLead: HttpResponse<ITeamLead>) => {
          if (teamLead.body) {
            return of(teamLead.body);
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

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeamLead, NewTeamLead } from '../team-lead.model';

export type PartialUpdateTeamLead = Partial<ITeamLead> & Pick<ITeamLead, 'id'>;

export type EntityResponseType = HttpResponse<ITeamLead>;
export type EntityArrayResponseType = HttpResponse<ITeamLead[]>;

@Injectable({ providedIn: 'root' })
export class TeamLeadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/team-leads');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teamLead: NewTeamLead): Observable<EntityResponseType> {
    return this.http.post<ITeamLead>(this.resourceUrl, teamLead, { observe: 'response' });
  }

  update(teamLead: ITeamLead): Observable<EntityResponseType> {
    return this.http.put<ITeamLead>(`${this.resourceUrl}/${this.getTeamLeadIdentifier(teamLead)}`, teamLead, { observe: 'response' });
  }

  partialUpdate(teamLead: PartialUpdateTeamLead): Observable<EntityResponseType> {
    return this.http.patch<ITeamLead>(`${this.resourceUrl}/${this.getTeamLeadIdentifier(teamLead)}`, teamLead, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITeamLead>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITeamLead[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTeamLeadIdentifier(teamLead: Pick<ITeamLead, 'id'>): string {
    return teamLead.id;
  }

  compareTeamLead(o1: Pick<ITeamLead, 'id'> | null, o2: Pick<ITeamLead, 'id'> | null): boolean {
    return o1 && o2 ? this.getTeamLeadIdentifier(o1) === this.getTeamLeadIdentifier(o2) : o1 === o2;
  }

  addTeamLeadToCollectionIfMissing<Type extends Pick<ITeamLead, 'id'>>(
    teamLeadCollection: Type[],
    ...teamLeadsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const teamLeads: Type[] = teamLeadsToCheck.filter(isPresent);
    if (teamLeads.length > 0) {
      const teamLeadCollectionIdentifiers = teamLeadCollection.map(teamLeadItem => this.getTeamLeadIdentifier(teamLeadItem)!);
      const teamLeadsToAdd = teamLeads.filter(teamLeadItem => {
        const teamLeadIdentifier = this.getTeamLeadIdentifier(teamLeadItem);
        if (teamLeadCollectionIdentifiers.includes(teamLeadIdentifier)) {
          return false;
        }
        teamLeadCollectionIdentifiers.push(teamLeadIdentifier);
        return true;
      });
      return [...teamLeadsToAdd, ...teamLeadCollection];
    }
    return teamLeadCollection;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IManagerial, NewManagerial } from '../managerial.model';

export type PartialUpdateManagerial = Partial<IManagerial> & Pick<IManagerial, 'id'>;

export type EntityResponseType = HttpResponse<IManagerial>;
export type EntityArrayResponseType = HttpResponse<IManagerial[]>;

@Injectable({ providedIn: 'root' })
export class ManagerialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/managerials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(managerial: NewManagerial): Observable<EntityResponseType> {
    return this.http.post<IManagerial>(this.resourceUrl, managerial, { observe: 'response' });
  }

  update(managerial: IManagerial): Observable<EntityResponseType> {
    return this.http.put<IManagerial>(`${this.resourceUrl}/${this.getManagerialIdentifier(managerial)}`, managerial, {
      observe: 'response',
    });
  }

  partialUpdate(managerial: PartialUpdateManagerial): Observable<EntityResponseType> {
    return this.http.patch<IManagerial>(`${this.resourceUrl}/${this.getManagerialIdentifier(managerial)}`, managerial, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IManagerial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IManagerial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getManagerialIdentifier(managerial: Pick<IManagerial, 'id'>): string {
    return managerial.id;
  }

  compareManagerial(o1: Pick<IManagerial, 'id'> | null, o2: Pick<IManagerial, 'id'> | null): boolean {
    return o1 && o2 ? this.getManagerialIdentifier(o1) === this.getManagerialIdentifier(o2) : o1 === o2;
  }

  addManagerialToCollectionIfMissing<Type extends Pick<IManagerial, 'id'>>(
    managerialCollection: Type[],
    ...managerialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const managerials: Type[] = managerialsToCheck.filter(isPresent);
    if (managerials.length > 0) {
      const managerialCollectionIdentifiers = managerialCollection.map(managerialItem => this.getManagerialIdentifier(managerialItem)!);
      const managerialsToAdd = managerials.filter(managerialItem => {
        const managerialIdentifier = this.getManagerialIdentifier(managerialItem);
        if (managerialCollectionIdentifiers.includes(managerialIdentifier)) {
          return false;
        }
        managerialCollectionIdentifiers.push(managerialIdentifier);
        return true;
      });
      return [...managerialsToAdd, ...managerialCollection];
    }
    return managerialCollection;
  }
}

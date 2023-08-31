import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFraudType, NewFraudType } from '../fraud-type.model';

export type PartialUpdateFraudType = Partial<IFraudType> & Pick<IFraudType, 'id'>;

export type EntityResponseType = HttpResponse<IFraudType>;
export type EntityArrayResponseType = HttpResponse<IFraudType[]>;

@Injectable({ providedIn: 'root' })
export class FraudTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fraud-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fraudType: NewFraudType): Observable<EntityResponseType> {
    return this.http.post<IFraudType>(this.resourceUrl, fraudType, { observe: 'response' });
  }

  update(fraudType: IFraudType): Observable<EntityResponseType> {
    return this.http.put<IFraudType>(`${this.resourceUrl}/${this.getFraudTypeIdentifier(fraudType)}`, fraudType, { observe: 'response' });
  }

  partialUpdate(fraudType: PartialUpdateFraudType): Observable<EntityResponseType> {
    return this.http.patch<IFraudType>(`${this.resourceUrl}/${this.getFraudTypeIdentifier(fraudType)}`, fraudType, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IFraudType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFraudType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFraudTypeIdentifier(fraudType: Pick<IFraudType, 'id'>): string {
    return fraudType.id;
  }

  compareFraudType(o1: Pick<IFraudType, 'id'> | null, o2: Pick<IFraudType, 'id'> | null): boolean {
    return o1 && o2 ? this.getFraudTypeIdentifier(o1) === this.getFraudTypeIdentifier(o2) : o1 === o2;
  }

  addFraudTypeToCollectionIfMissing<Type extends Pick<IFraudType, 'id'>>(
    fraudTypeCollection: Type[],
    ...fraudTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fraudTypes: Type[] = fraudTypesToCheck.filter(isPresent);
    if (fraudTypes.length > 0) {
      const fraudTypeCollectionIdentifiers = fraudTypeCollection.map(fraudTypeItem => this.getFraudTypeIdentifier(fraudTypeItem)!);
      const fraudTypesToAdd = fraudTypes.filter(fraudTypeItem => {
        const fraudTypeIdentifier = this.getFraudTypeIdentifier(fraudTypeItem);
        if (fraudTypeCollectionIdentifiers.includes(fraudTypeIdentifier)) {
          return false;
        }
        fraudTypeCollectionIdentifiers.push(fraudTypeIdentifier);
        return true;
      });
      return [...fraudTypesToAdd, ...fraudTypeCollection];
    }
    return fraudTypeCollection;
  }
}

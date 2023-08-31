import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBankService, NewBankService } from '../bank-service.model';

export type PartialUpdateBankService = Partial<IBankService> & Pick<IBankService, 'id'>;

export type EntityResponseType = HttpResponse<IBankService>;
export type EntityArrayResponseType = HttpResponse<IBankService[]>;

@Injectable({ providedIn: 'root' })
export class BankServiceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bank-services');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bankService: NewBankService): Observable<EntityResponseType> {
    return this.http.post<IBankService>(this.resourceUrl, bankService, { observe: 'response' });
  }

  update(bankService: IBankService): Observable<EntityResponseType> {
    return this.http.put<IBankService>(`${this.resourceUrl}/${this.getBankServiceIdentifier(bankService)}`, bankService, {
      observe: 'response',
    });
  }

  partialUpdate(bankService: PartialUpdateBankService): Observable<EntityResponseType> {
    return this.http.patch<IBankService>(`${this.resourceUrl}/${this.getBankServiceIdentifier(bankService)}`, bankService, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IBankService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBankServiceIdentifier(bankService: Pick<IBankService, 'id'>): string {
    return bankService.id;
  }

  compareBankService(o1: Pick<IBankService, 'id'> | null, o2: Pick<IBankService, 'id'> | null): boolean {
    return o1 && o2 ? this.getBankServiceIdentifier(o1) === this.getBankServiceIdentifier(o2) : o1 === o2;
  }

  addBankServiceToCollectionIfMissing<Type extends Pick<IBankService, 'id'>>(
    bankServiceCollection: Type[],
    ...bankServicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bankServices: Type[] = bankServicesToCheck.filter(isPresent);
    if (bankServices.length > 0) {
      const bankServiceCollectionIdentifiers = bankServiceCollection.map(
        bankServiceItem => this.getBankServiceIdentifier(bankServiceItem)!
      );
      const bankServicesToAdd = bankServices.filter(bankServiceItem => {
        const bankServiceIdentifier = this.getBankServiceIdentifier(bankServiceItem);
        if (bankServiceCollectionIdentifiers.includes(bankServiceIdentifier)) {
          return false;
        }
        bankServiceCollectionIdentifiers.push(bankServiceIdentifier);
        return true;
      });
      return [...bankServicesToAdd, ...bankServiceCollection];
    }
    return bankServiceCollection;
  }
}

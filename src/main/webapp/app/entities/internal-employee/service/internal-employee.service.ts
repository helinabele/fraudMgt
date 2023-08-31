import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInternalEmployee, NewInternalEmployee } from '../internal-employee.model';

export type PartialUpdateInternalEmployee = Partial<IInternalEmployee> & Pick<IInternalEmployee, 'id'>;

export type EntityResponseType = HttpResponse<IInternalEmployee>;
export type EntityArrayResponseType = HttpResponse<IInternalEmployee[]>;

@Injectable({ providedIn: 'root' })
export class InternalEmployeeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/internal-employees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(internalEmployee: NewInternalEmployee): Observable<EntityResponseType> {
    return this.http.post<IInternalEmployee>(this.resourceUrl, internalEmployee, { observe: 'response' });
  }

  update(internalEmployee: IInternalEmployee): Observable<EntityResponseType> {
    return this.http.put<IInternalEmployee>(
      `${this.resourceUrl}/${this.getInternalEmployeeIdentifier(internalEmployee)}`,
      internalEmployee,
      { observe: 'response' }
    );
  }

  partialUpdate(internalEmployee: PartialUpdateInternalEmployee): Observable<EntityResponseType> {
    return this.http.patch<IInternalEmployee>(
      `${this.resourceUrl}/${this.getInternalEmployeeIdentifier(internalEmployee)}`,
      internalEmployee,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IInternalEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInternalEmployee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInternalEmployeeIdentifier(internalEmployee: Pick<IInternalEmployee, 'id'>): string {
    return internalEmployee.id;
  }

  compareInternalEmployee(o1: Pick<IInternalEmployee, 'id'> | null, o2: Pick<IInternalEmployee, 'id'> | null): boolean {
    return o1 && o2 ? this.getInternalEmployeeIdentifier(o1) === this.getInternalEmployeeIdentifier(o2) : o1 === o2;
  }

  addInternalEmployeeToCollectionIfMissing<Type extends Pick<IInternalEmployee, 'id'>>(
    internalEmployeeCollection: Type[],
    ...internalEmployeesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const internalEmployees: Type[] = internalEmployeesToCheck.filter(isPresent);
    if (internalEmployees.length > 0) {
      const internalEmployeeCollectionIdentifiers = internalEmployeeCollection.map(
        internalEmployeeItem => this.getInternalEmployeeIdentifier(internalEmployeeItem)!
      );
      const internalEmployeesToAdd = internalEmployees.filter(internalEmployeeItem => {
        const internalEmployeeIdentifier = this.getInternalEmployeeIdentifier(internalEmployeeItem);
        if (internalEmployeeCollectionIdentifiers.includes(internalEmployeeIdentifier)) {
          return false;
        }
        internalEmployeeCollectionIdentifiers.push(internalEmployeeIdentifier);
        return true;
      });
      return [...internalEmployeesToAdd, ...internalEmployeeCollection];
    }
    return internalEmployeeCollection;
  }
}

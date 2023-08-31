import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExternalEmployee, NewExternalEmployee } from '../external-employee.model';

export type PartialUpdateExternalEmployee = Partial<IExternalEmployee> & Pick<IExternalEmployee, 'id'>;

export type EntityResponseType = HttpResponse<IExternalEmployee>;
export type EntityArrayResponseType = HttpResponse<IExternalEmployee[]>;

@Injectable({ providedIn: 'root' })
export class ExternalEmployeeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/external-employees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(externalEmployee: NewExternalEmployee): Observable<EntityResponseType> {
    return this.http.post<IExternalEmployee>(this.resourceUrl, externalEmployee, { observe: 'response' });
  }

  update(externalEmployee: IExternalEmployee): Observable<EntityResponseType> {
    return this.http.put<IExternalEmployee>(
      `${this.resourceUrl}/${this.getExternalEmployeeIdentifier(externalEmployee)}`,
      externalEmployee,
      { observe: 'response' }
    );
  }

  partialUpdate(externalEmployee: PartialUpdateExternalEmployee): Observable<EntityResponseType> {
    return this.http.patch<IExternalEmployee>(
      `${this.resourceUrl}/${this.getExternalEmployeeIdentifier(externalEmployee)}`,
      externalEmployee,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IExternalEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExternalEmployee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExternalEmployeeIdentifier(externalEmployee: Pick<IExternalEmployee, 'id'>): string {
    return externalEmployee.id;
  }

  compareExternalEmployee(o1: Pick<IExternalEmployee, 'id'> | null, o2: Pick<IExternalEmployee, 'id'> | null): boolean {
    return o1 && o2 ? this.getExternalEmployeeIdentifier(o1) === this.getExternalEmployeeIdentifier(o2) : o1 === o2;
  }

  addExternalEmployeeToCollectionIfMissing<Type extends Pick<IExternalEmployee, 'id'>>(
    externalEmployeeCollection: Type[],
    ...externalEmployeesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const externalEmployees: Type[] = externalEmployeesToCheck.filter(isPresent);
    if (externalEmployees.length > 0) {
      const externalEmployeeCollectionIdentifiers = externalEmployeeCollection.map(
        externalEmployeeItem => this.getExternalEmployeeIdentifier(externalEmployeeItem)!
      );
      const externalEmployeesToAdd = externalEmployees.filter(externalEmployeeItem => {
        const externalEmployeeIdentifier = this.getExternalEmployeeIdentifier(externalEmployeeItem);
        if (externalEmployeeCollectionIdentifiers.includes(externalEmployeeIdentifier)) {
          return false;
        }
        externalEmployeeCollectionIdentifiers.push(externalEmployeeIdentifier);
        return true;
      });
      return [...externalEmployeesToAdd, ...externalEmployeeCollection];
    }
    return externalEmployeeCollection;
  }
}

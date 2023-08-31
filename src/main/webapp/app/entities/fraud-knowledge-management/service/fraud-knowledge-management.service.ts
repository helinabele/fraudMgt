import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFraudKnowledgeManagement, NewFraudKnowledgeManagement } from '../fraud-knowledge-management.model';

export type PartialUpdateFraudKnowledgeManagement = Partial<IFraudKnowledgeManagement> & Pick<IFraudKnowledgeManagement, 'id'>;

type RestOf<T extends IFraudKnowledgeManagement | NewFraudKnowledgeManagement> = Omit<
  T,
  'incidentDate' | 'dateOfDetection' | 'projectCreationDate' | 'reportDate'
> & {
  incidentDate?: string | null;
  dateOfDetection?: string | null;
  projectCreationDate?: string | null;
  reportDate?: string | null;
};

export type RestFraudKnowledgeManagement = RestOf<IFraudKnowledgeManagement>;

export type NewRestFraudKnowledgeManagement = RestOf<NewFraudKnowledgeManagement>;

export type PartialUpdateRestFraudKnowledgeManagement = RestOf<PartialUpdateFraudKnowledgeManagement>;

export type EntityResponseType = HttpResponse<IFraudKnowledgeManagement>;
export type EntityArrayResponseType = HttpResponse<IFraudKnowledgeManagement[]>;

@Injectable({ providedIn: 'root' })
export class FraudKnowledgeManagementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fraud-knowledge-managements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}
  getFraudKnowledgeManagements(): Observable<IFraudKnowledgeManagement[]> {
    return this.http.get<IFraudKnowledgeManagement[]>(this.resourceUrl);
  }
  getFraudKnowledgeManagementsByDateRange(startDate?: Date, endDate?: Date): Observable<IFraudKnowledgeManagement[]> {
    let params = new HttpParams();
    if (startDate) {
      params = params.set('startDate', startDate.toISOString());
    }
    if (endDate) {
      params = params.set('endDate', endDate.toISOString());
    }
    return this.http.get<IFraudKnowledgeManagement[]>(this.resourceUrl, { params });
  }
  getAll(): Observable<any[]> {
    return this.http.get<any[]>(`${this.resourceUrl}`);
  }
  create(fraudKnowledgeManagement: NewFraudKnowledgeManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fraudKnowledgeManagement);
    return this.http
      .post<RestFraudKnowledgeManagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(fraudKnowledgeManagement: IFraudKnowledgeManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fraudKnowledgeManagement);
    return this.http
      .put<RestFraudKnowledgeManagement>(
        `${this.resourceUrl}/${this.getFraudKnowledgeManagementIdentifier(fraudKnowledgeManagement)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(fraudKnowledgeManagement: PartialUpdateFraudKnowledgeManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fraudKnowledgeManagement);
    return this.http
      .patch<RestFraudKnowledgeManagement>(
        `${this.resourceUrl}/${this.getFraudKnowledgeManagementIdentifier(fraudKnowledgeManagement)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestFraudKnowledgeManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFraudKnowledgeManagement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFraudKnowledgeManagementIdentifier(fraudKnowledgeManagement: Pick<IFraudKnowledgeManagement, 'id'>): string {
    return fraudKnowledgeManagement.id;
  }

  compareFraudKnowledgeManagement(
    o1: Pick<IFraudKnowledgeManagement, 'id'> | null,
    o2: Pick<IFraudKnowledgeManagement, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getFraudKnowledgeManagementIdentifier(o1) === this.getFraudKnowledgeManagementIdentifier(o2) : o1 === o2;
  }

  addFraudKnowledgeManagementToCollectionIfMissing<Type extends Pick<IFraudKnowledgeManagement, 'id'>>(
    fraudKnowledgeManagementCollection: Type[],
    ...fraudKnowledgeManagementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fraudKnowledgeManagements: Type[] = fraudKnowledgeManagementsToCheck.filter(isPresent);
    if (fraudKnowledgeManagements.length > 0) {
      const fraudKnowledgeManagementCollectionIdentifiers = fraudKnowledgeManagementCollection.map(
        fraudKnowledgeManagementItem => this.getFraudKnowledgeManagementIdentifier(fraudKnowledgeManagementItem)!
      );
      const fraudKnowledgeManagementsToAdd = fraudKnowledgeManagements.filter(fraudKnowledgeManagementItem => {
        const fraudKnowledgeManagementIdentifier = this.getFraudKnowledgeManagementIdentifier(fraudKnowledgeManagementItem);
        if (fraudKnowledgeManagementCollectionIdentifiers.includes(fraudKnowledgeManagementIdentifier)) {
          return false;
        }
        fraudKnowledgeManagementCollectionIdentifiers.push(fraudKnowledgeManagementIdentifier);
        return true;
      });
      return [...fraudKnowledgeManagementsToAdd, ...fraudKnowledgeManagementCollection];
    }
    return fraudKnowledgeManagementCollection;
  }

  protected convertDateFromClient<
    T extends IFraudKnowledgeManagement | NewFraudKnowledgeManagement | PartialUpdateFraudKnowledgeManagement
  >(fraudKnowledgeManagement: T): RestOf<T> {
    return {
      ...fraudKnowledgeManagement,
      incidentDate: fraudKnowledgeManagement.incidentDate?.toJSON() ?? null,
      dateOfDetection: fraudKnowledgeManagement.dateOfDetection?.toJSON() ?? null,
      projectCreationDate: fraudKnowledgeManagement.projectCreationDate?.format(DATE_FORMAT) ?? null,
      reportDate: fraudKnowledgeManagement.reportDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFraudKnowledgeManagement: RestFraudKnowledgeManagement): IFraudKnowledgeManagement {
    return {
      ...restFraudKnowledgeManagement,
      incidentDate: restFraudKnowledgeManagement.incidentDate ? dayjs(restFraudKnowledgeManagement.incidentDate) : undefined,
      dateOfDetection: restFraudKnowledgeManagement.dateOfDetection ? dayjs(restFraudKnowledgeManagement.dateOfDetection) : undefined,
      projectCreationDate: restFraudKnowledgeManagement.projectCreationDate
        ? dayjs(restFraudKnowledgeManagement.projectCreationDate)
        : undefined,
      reportDate: restFraudKnowledgeManagement.reportDate ? dayjs(restFraudKnowledgeManagement.reportDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFraudKnowledgeManagement>): HttpResponse<IFraudKnowledgeManagement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFraudKnowledgeManagement[]>): HttpResponse<IFraudKnowledgeManagement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

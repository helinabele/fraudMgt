import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFraudInvestigationReport } from '../fraud-investigation-report.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../fraud-investigation-report.test-samples';

import { FraudInvestigationReportService, RestFraudInvestigationReport } from './fraud-investigation-report.service';

const requireRestSample: RestFraudInvestigationReport = {
  ...sampleWithRequiredData,
  publicationDate: sampleWithRequiredData.publicationDate?.toJSON(),
};

describe('FraudInvestigationReport Service', () => {
  let service: FraudInvestigationReportService;
  let httpMock: HttpTestingController;
  let expectedResult: IFraudInvestigationReport | IFraudInvestigationReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FraudInvestigationReportService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FraudInvestigationReport', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fraudInvestigationReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fraudInvestigationReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FraudInvestigationReport', () => {
      const fraudInvestigationReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fraudInvestigationReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FraudInvestigationReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FraudInvestigationReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FraudInvestigationReport', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFraudInvestigationReportToCollectionIfMissing', () => {
      it('should add a FraudInvestigationReport to an empty array', () => {
        const fraudInvestigationReport: IFraudInvestigationReport = sampleWithRequiredData;
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing([], fraudInvestigationReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fraudInvestigationReport);
      });

      it('should not add a FraudInvestigationReport to an array that contains it', () => {
        const fraudInvestigationReport: IFraudInvestigationReport = sampleWithRequiredData;
        const fraudInvestigationReportCollection: IFraudInvestigationReport[] = [
          {
            ...fraudInvestigationReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing(
          fraudInvestigationReportCollection,
          fraudInvestigationReport
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FraudInvestigationReport to an array that doesn't contain it", () => {
        const fraudInvestigationReport: IFraudInvestigationReport = sampleWithRequiredData;
        const fraudInvestigationReportCollection: IFraudInvestigationReport[] = [sampleWithPartialData];
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing(
          fraudInvestigationReportCollection,
          fraudInvestigationReport
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fraudInvestigationReport);
      });

      it('should add only unique FraudInvestigationReport to an array', () => {
        const fraudInvestigationReportArray: IFraudInvestigationReport[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const fraudInvestigationReportCollection: IFraudInvestigationReport[] = [sampleWithRequiredData];
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing(
          fraudInvestigationReportCollection,
          ...fraudInvestigationReportArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fraudInvestigationReport: IFraudInvestigationReport = sampleWithRequiredData;
        const fraudInvestigationReport2: IFraudInvestigationReport = sampleWithPartialData;
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing([], fraudInvestigationReport, fraudInvestigationReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fraudInvestigationReport);
        expect(expectedResult).toContain(fraudInvestigationReport2);
      });

      it('should accept null and undefined values', () => {
        const fraudInvestigationReport: IFraudInvestigationReport = sampleWithRequiredData;
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing([], null, fraudInvestigationReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fraudInvestigationReport);
      });

      it('should return initial array if no FraudInvestigationReport is added', () => {
        const fraudInvestigationReportCollection: IFraudInvestigationReport[] = [sampleWithRequiredData];
        expectedResult = service.addFraudInvestigationReportToCollectionIfMissing(fraudInvestigationReportCollection, undefined, null);
        expect(expectedResult).toEqual(fraudInvestigationReportCollection);
      });
    });

    describe('compareFraudInvestigationReport', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFraudInvestigationReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareFraudInvestigationReport(entity1, entity2);
        const compareResult2 = service.compareFraudInvestigationReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareFraudInvestigationReport(entity1, entity2);
        const compareResult2 = service.compareFraudInvestigationReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareFraudInvestigationReport(entity1, entity2);
        const compareResult2 = service.compareFraudInvestigationReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

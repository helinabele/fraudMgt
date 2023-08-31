import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWhistleBlowerReport } from '../whistle-blower-report.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../whistle-blower-report.test-samples';

import { WhistleBlowerReportService } from './whistle-blower-report.service';

const requireRestSample: IWhistleBlowerReport = {
  ...sampleWithRequiredData,
};

describe('WhistleBlowerReport Service', () => {
  let service: WhistleBlowerReportService;
  let httpMock: HttpTestingController;
  let expectedResult: IWhistleBlowerReport | IWhistleBlowerReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WhistleBlowerReportService);
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

    it('should create a WhistleBlowerReport', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const whistleBlowerReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(whistleBlowerReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WhistleBlowerReport', () => {
      const whistleBlowerReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(whistleBlowerReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WhistleBlowerReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WhistleBlowerReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WhistleBlowerReport', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWhistleBlowerReportToCollectionIfMissing', () => {
      it('should add a WhistleBlowerReport to an empty array', () => {
        const whistleBlowerReport: IWhistleBlowerReport = sampleWithRequiredData;
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing([], whistleBlowerReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(whistleBlowerReport);
      });

      it('should not add a WhistleBlowerReport to an array that contains it', () => {
        const whistleBlowerReport: IWhistleBlowerReport = sampleWithRequiredData;
        const whistleBlowerReportCollection: IWhistleBlowerReport[] = [
          {
            ...whistleBlowerReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing(whistleBlowerReportCollection, whistleBlowerReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WhistleBlowerReport to an array that doesn't contain it", () => {
        const whistleBlowerReport: IWhistleBlowerReport = sampleWithRequiredData;
        const whistleBlowerReportCollection: IWhistleBlowerReport[] = [sampleWithPartialData];
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing(whistleBlowerReportCollection, whistleBlowerReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(whistleBlowerReport);
      });

      it('should add only unique WhistleBlowerReport to an array', () => {
        const whistleBlowerReportArray: IWhistleBlowerReport[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const whistleBlowerReportCollection: IWhistleBlowerReport[] = [sampleWithRequiredData];
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing(whistleBlowerReportCollection, ...whistleBlowerReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const whistleBlowerReport: IWhistleBlowerReport = sampleWithRequiredData;
        const whistleBlowerReport2: IWhistleBlowerReport = sampleWithPartialData;
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing([], whistleBlowerReport, whistleBlowerReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(whistleBlowerReport);
        expect(expectedResult).toContain(whistleBlowerReport2);
      });

      it('should accept null and undefined values', () => {
        const whistleBlowerReport: IWhistleBlowerReport = sampleWithRequiredData;
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing([], null, whistleBlowerReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(whistleBlowerReport);
      });

      it('should return initial array if no WhistleBlowerReport is added', () => {
        const whistleBlowerReportCollection: IWhistleBlowerReport[] = [sampleWithRequiredData];
        expectedResult = service.addWhistleBlowerReportToCollectionIfMissing(whistleBlowerReportCollection, undefined, null);
        expect(expectedResult).toEqual(whistleBlowerReportCollection);
      });
    });

    describe('compareWhistleBlowerReport', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWhistleBlowerReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareWhistleBlowerReport(entity1, entity2);
        const compareResult2 = service.compareWhistleBlowerReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareWhistleBlowerReport(entity1, entity2);
        const compareResult2 = service.compareWhistleBlowerReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareWhistleBlowerReport(entity1, entity2);
        const compareResult2 = service.compareWhistleBlowerReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

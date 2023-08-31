import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFindings } from '../findings.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../findings.test-samples';

import { FindingsService } from './findings.service';

const requireRestSample: IFindings = {
  ...sampleWithRequiredData,
};

describe('Findings Service', () => {
  let service: FindingsService;
  let httpMock: HttpTestingController;
  let expectedResult: IFindings | IFindings[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FindingsService);
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

    it('should create a Findings', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const findings = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(findings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Findings', () => {
      const findings = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(findings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Findings', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Findings', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Findings', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFindingsToCollectionIfMissing', () => {
      it('should add a Findings to an empty array', () => {
        const findings: IFindings = sampleWithRequiredData;
        expectedResult = service.addFindingsToCollectionIfMissing([], findings);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(findings);
      });

      it('should not add a Findings to an array that contains it', () => {
        const findings: IFindings = sampleWithRequiredData;
        const findingsCollection: IFindings[] = [
          {
            ...findings,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFindingsToCollectionIfMissing(findingsCollection, findings);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Findings to an array that doesn't contain it", () => {
        const findings: IFindings = sampleWithRequiredData;
        const findingsCollection: IFindings[] = [sampleWithPartialData];
        expectedResult = service.addFindingsToCollectionIfMissing(findingsCollection, findings);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(findings);
      });

      it('should add only unique Findings to an array', () => {
        const findingsArray: IFindings[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const findingsCollection: IFindings[] = [sampleWithRequiredData];
        expectedResult = service.addFindingsToCollectionIfMissing(findingsCollection, ...findingsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const findings: IFindings = sampleWithRequiredData;
        const findings2: IFindings = sampleWithPartialData;
        expectedResult = service.addFindingsToCollectionIfMissing([], findings, findings2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(findings);
        expect(expectedResult).toContain(findings2);
      });

      it('should accept null and undefined values', () => {
        const findings: IFindings = sampleWithRequiredData;
        expectedResult = service.addFindingsToCollectionIfMissing([], null, findings, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(findings);
      });

      it('should return initial array if no Findings is added', () => {
        const findingsCollection: IFindings[] = [sampleWithRequiredData];
        expectedResult = service.addFindingsToCollectionIfMissing(findingsCollection, undefined, null);
        expect(expectedResult).toEqual(findingsCollection);
      });
    });

    describe('compareFindings', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFindings(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareFindings(entity1, entity2);
        const compareResult2 = service.compareFindings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareFindings(entity1, entity2);
        const compareResult2 = service.compareFindings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareFindings(entity1, entity2);
        const compareResult2 = service.compareFindings(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

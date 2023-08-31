import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFraudType } from '../fraud-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fraud-type.test-samples';

import { FraudTypeService } from './fraud-type.service';

const requireRestSample: IFraudType = {
  ...sampleWithRequiredData,
};

describe('FraudType Service', () => {
  let service: FraudTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IFraudType | IFraudType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FraudTypeService);
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

    it('should create a FraudType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fraudType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fraudType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FraudType', () => {
      const fraudType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fraudType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FraudType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FraudType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FraudType', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFraudTypeToCollectionIfMissing', () => {
      it('should add a FraudType to an empty array', () => {
        const fraudType: IFraudType = sampleWithRequiredData;
        expectedResult = service.addFraudTypeToCollectionIfMissing([], fraudType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fraudType);
      });

      it('should not add a FraudType to an array that contains it', () => {
        const fraudType: IFraudType = sampleWithRequiredData;
        const fraudTypeCollection: IFraudType[] = [
          {
            ...fraudType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFraudTypeToCollectionIfMissing(fraudTypeCollection, fraudType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FraudType to an array that doesn't contain it", () => {
        const fraudType: IFraudType = sampleWithRequiredData;
        const fraudTypeCollection: IFraudType[] = [sampleWithPartialData];
        expectedResult = service.addFraudTypeToCollectionIfMissing(fraudTypeCollection, fraudType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fraudType);
      });

      it('should add only unique FraudType to an array', () => {
        const fraudTypeArray: IFraudType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fraudTypeCollection: IFraudType[] = [sampleWithRequiredData];
        expectedResult = service.addFraudTypeToCollectionIfMissing(fraudTypeCollection, ...fraudTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fraudType: IFraudType = sampleWithRequiredData;
        const fraudType2: IFraudType = sampleWithPartialData;
        expectedResult = service.addFraudTypeToCollectionIfMissing([], fraudType, fraudType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fraudType);
        expect(expectedResult).toContain(fraudType2);
      });

      it('should accept null and undefined values', () => {
        const fraudType: IFraudType = sampleWithRequiredData;
        expectedResult = service.addFraudTypeToCollectionIfMissing([], null, fraudType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fraudType);
      });

      it('should return initial array if no FraudType is added', () => {
        const fraudTypeCollection: IFraudType[] = [sampleWithRequiredData];
        expectedResult = service.addFraudTypeToCollectionIfMissing(fraudTypeCollection, undefined, null);
        expect(expectedResult).toEqual(fraudTypeCollection);
      });
    });

    describe('compareFraudType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFraudType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareFraudType(entity1, entity2);
        const compareResult2 = service.compareFraudType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareFraudType(entity1, entity2);
        const compareResult2 = service.compareFraudType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareFraudType(entity1, entity2);
        const compareResult2 = service.compareFraudType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

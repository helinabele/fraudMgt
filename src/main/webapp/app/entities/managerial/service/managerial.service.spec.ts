import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IManagerial } from '../managerial.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../managerial.test-samples';

import { ManagerialService } from './managerial.service';

const requireRestSample: IManagerial = {
  ...sampleWithRequiredData,
};

describe('Managerial Service', () => {
  let service: ManagerialService;
  let httpMock: HttpTestingController;
  let expectedResult: IManagerial | IManagerial[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ManagerialService);
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

    it('should create a Managerial', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const managerial = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(managerial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Managerial', () => {
      const managerial = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(managerial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Managerial', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Managerial', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Managerial', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addManagerialToCollectionIfMissing', () => {
      it('should add a Managerial to an empty array', () => {
        const managerial: IManagerial = sampleWithRequiredData;
        expectedResult = service.addManagerialToCollectionIfMissing([], managerial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managerial);
      });

      it('should not add a Managerial to an array that contains it', () => {
        const managerial: IManagerial = sampleWithRequiredData;
        const managerialCollection: IManagerial[] = [
          {
            ...managerial,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addManagerialToCollectionIfMissing(managerialCollection, managerial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Managerial to an array that doesn't contain it", () => {
        const managerial: IManagerial = sampleWithRequiredData;
        const managerialCollection: IManagerial[] = [sampleWithPartialData];
        expectedResult = service.addManagerialToCollectionIfMissing(managerialCollection, managerial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managerial);
      });

      it('should add only unique Managerial to an array', () => {
        const managerialArray: IManagerial[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const managerialCollection: IManagerial[] = [sampleWithRequiredData];
        expectedResult = service.addManagerialToCollectionIfMissing(managerialCollection, ...managerialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const managerial: IManagerial = sampleWithRequiredData;
        const managerial2: IManagerial = sampleWithPartialData;
        expectedResult = service.addManagerialToCollectionIfMissing([], managerial, managerial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managerial);
        expect(expectedResult).toContain(managerial2);
      });

      it('should accept null and undefined values', () => {
        const managerial: IManagerial = sampleWithRequiredData;
        expectedResult = service.addManagerialToCollectionIfMissing([], null, managerial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managerial);
      });

      it('should return initial array if no Managerial is added', () => {
        const managerialCollection: IManagerial[] = [sampleWithRequiredData];
        expectedResult = service.addManagerialToCollectionIfMissing(managerialCollection, undefined, null);
        expect(expectedResult).toEqual(managerialCollection);
      });
    });

    describe('compareManagerial', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareManagerial(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareManagerial(entity1, entity2);
        const compareResult2 = service.compareManagerial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareManagerial(entity1, entity2);
        const compareResult2 = service.compareManagerial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareManagerial(entity1, entity2);
        const compareResult2 = service.compareManagerial(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

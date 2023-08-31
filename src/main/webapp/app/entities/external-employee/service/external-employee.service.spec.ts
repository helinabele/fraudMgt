import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IExternalEmployee } from '../external-employee.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../external-employee.test-samples';

import { ExternalEmployeeService } from './external-employee.service';

const requireRestSample: IExternalEmployee = {
  ...sampleWithRequiredData,
};

describe('ExternalEmployee Service', () => {
  let service: ExternalEmployeeService;
  let httpMock: HttpTestingController;
  let expectedResult: IExternalEmployee | IExternalEmployee[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExternalEmployeeService);
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

    it('should create a ExternalEmployee', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const externalEmployee = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(externalEmployee).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExternalEmployee', () => {
      const externalEmployee = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(externalEmployee).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ExternalEmployee', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ExternalEmployee', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ExternalEmployee', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addExternalEmployeeToCollectionIfMissing', () => {
      it('should add a ExternalEmployee to an empty array', () => {
        const externalEmployee: IExternalEmployee = sampleWithRequiredData;
        expectedResult = service.addExternalEmployeeToCollectionIfMissing([], externalEmployee);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(externalEmployee);
      });

      it('should not add a ExternalEmployee to an array that contains it', () => {
        const externalEmployee: IExternalEmployee = sampleWithRequiredData;
        const externalEmployeeCollection: IExternalEmployee[] = [
          {
            ...externalEmployee,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addExternalEmployeeToCollectionIfMissing(externalEmployeeCollection, externalEmployee);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExternalEmployee to an array that doesn't contain it", () => {
        const externalEmployee: IExternalEmployee = sampleWithRequiredData;
        const externalEmployeeCollection: IExternalEmployee[] = [sampleWithPartialData];
        expectedResult = service.addExternalEmployeeToCollectionIfMissing(externalEmployeeCollection, externalEmployee);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(externalEmployee);
      });

      it('should add only unique ExternalEmployee to an array', () => {
        const externalEmployeeArray: IExternalEmployee[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const externalEmployeeCollection: IExternalEmployee[] = [sampleWithRequiredData];
        expectedResult = service.addExternalEmployeeToCollectionIfMissing(externalEmployeeCollection, ...externalEmployeeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const externalEmployee: IExternalEmployee = sampleWithRequiredData;
        const externalEmployee2: IExternalEmployee = sampleWithPartialData;
        expectedResult = service.addExternalEmployeeToCollectionIfMissing([], externalEmployee, externalEmployee2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(externalEmployee);
        expect(expectedResult).toContain(externalEmployee2);
      });

      it('should accept null and undefined values', () => {
        const externalEmployee: IExternalEmployee = sampleWithRequiredData;
        expectedResult = service.addExternalEmployeeToCollectionIfMissing([], null, externalEmployee, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(externalEmployee);
      });

      it('should return initial array if no ExternalEmployee is added', () => {
        const externalEmployeeCollection: IExternalEmployee[] = [sampleWithRequiredData];
        expectedResult = service.addExternalEmployeeToCollectionIfMissing(externalEmployeeCollection, undefined, null);
        expect(expectedResult).toEqual(externalEmployeeCollection);
      });
    });

    describe('compareExternalEmployee', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareExternalEmployee(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareExternalEmployee(entity1, entity2);
        const compareResult2 = service.compareExternalEmployee(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareExternalEmployee(entity1, entity2);
        const compareResult2 = service.compareExternalEmployee(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareExternalEmployee(entity1, entity2);
        const compareResult2 = service.compareExternalEmployee(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

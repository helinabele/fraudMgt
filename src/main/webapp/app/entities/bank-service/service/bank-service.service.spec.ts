import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBankService } from '../bank-service.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bank-service.test-samples';

import { BankServiceService } from './bank-service.service';

const requireRestSample: IBankService = {
  ...sampleWithRequiredData,
};

describe('BankService Service', () => {
  let service: BankServiceService;
  let httpMock: HttpTestingController;
  let expectedResult: IBankService | IBankService[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BankServiceService);
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

    it('should create a BankService', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const bankService = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bankService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BankService', () => {
      const bankService = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bankService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BankService', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BankService', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BankService', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBankServiceToCollectionIfMissing', () => {
      it('should add a BankService to an empty array', () => {
        const bankService: IBankService = sampleWithRequiredData;
        expectedResult = service.addBankServiceToCollectionIfMissing([], bankService);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankService);
      });

      it('should not add a BankService to an array that contains it', () => {
        const bankService: IBankService = sampleWithRequiredData;
        const bankServiceCollection: IBankService[] = [
          {
            ...bankService,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBankServiceToCollectionIfMissing(bankServiceCollection, bankService);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BankService to an array that doesn't contain it", () => {
        const bankService: IBankService = sampleWithRequiredData;
        const bankServiceCollection: IBankService[] = [sampleWithPartialData];
        expectedResult = service.addBankServiceToCollectionIfMissing(bankServiceCollection, bankService);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankService);
      });

      it('should add only unique BankService to an array', () => {
        const bankServiceArray: IBankService[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bankServiceCollection: IBankService[] = [sampleWithRequiredData];
        expectedResult = service.addBankServiceToCollectionIfMissing(bankServiceCollection, ...bankServiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bankService: IBankService = sampleWithRequiredData;
        const bankService2: IBankService = sampleWithPartialData;
        expectedResult = service.addBankServiceToCollectionIfMissing([], bankService, bankService2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankService);
        expect(expectedResult).toContain(bankService2);
      });

      it('should accept null and undefined values', () => {
        const bankService: IBankService = sampleWithRequiredData;
        expectedResult = service.addBankServiceToCollectionIfMissing([], null, bankService, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankService);
      });

      it('should return initial array if no BankService is added', () => {
        const bankServiceCollection: IBankService[] = [sampleWithRequiredData];
        expectedResult = service.addBankServiceToCollectionIfMissing(bankServiceCollection, undefined, null);
        expect(expectedResult).toEqual(bankServiceCollection);
      });
    });

    describe('compareBankService', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBankService(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareBankService(entity1, entity2);
        const compareResult2 = service.compareBankService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareBankService(entity1, entity2);
        const compareResult2 = service.compareBankService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareBankService(entity1, entity2);
        const compareResult2 = service.compareBankService(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

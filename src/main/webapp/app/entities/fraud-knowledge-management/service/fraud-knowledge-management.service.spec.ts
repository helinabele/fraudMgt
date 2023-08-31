import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFraudKnowledgeManagement } from '../fraud-knowledge-management.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../fraud-knowledge-management.test-samples';

import { FraudKnowledgeManagementService, RestFraudKnowledgeManagement } from './fraud-knowledge-management.service';

const requireRestSample: RestFraudKnowledgeManagement = {
  ...sampleWithRequiredData,
  incidentDate: sampleWithRequiredData.incidentDate?.toJSON(),
  dateOfDetection: sampleWithRequiredData.dateOfDetection?.toJSON(),
  projectCreationDate: sampleWithRequiredData.projectCreationDate?.format(DATE_FORMAT),
  reportDate: sampleWithRequiredData.reportDate?.format(DATE_FORMAT),
};

describe('FraudKnowledgeManagement Service', () => {
  let service: FraudKnowledgeManagementService;
  let httpMock: HttpTestingController;
  let expectedResult: IFraudKnowledgeManagement | IFraudKnowledgeManagement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FraudKnowledgeManagementService);
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

    it('should create a FraudKnowledgeManagement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fraudKnowledgeManagement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fraudKnowledgeManagement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FraudKnowledgeManagement', () => {
      const fraudKnowledgeManagement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fraudKnowledgeManagement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FraudKnowledgeManagement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FraudKnowledgeManagement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FraudKnowledgeManagement', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFraudKnowledgeManagementToCollectionIfMissing', () => {
      it('should add a FraudKnowledgeManagement to an empty array', () => {
        const fraudKnowledgeManagement: IFraudKnowledgeManagement = sampleWithRequiredData;
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing([], fraudKnowledgeManagement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fraudKnowledgeManagement);
      });

      it('should not add a FraudKnowledgeManagement to an array that contains it', () => {
        const fraudKnowledgeManagement: IFraudKnowledgeManagement = sampleWithRequiredData;
        const fraudKnowledgeManagementCollection: IFraudKnowledgeManagement[] = [
          {
            ...fraudKnowledgeManagement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing(
          fraudKnowledgeManagementCollection,
          fraudKnowledgeManagement
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FraudKnowledgeManagement to an array that doesn't contain it", () => {
        const fraudKnowledgeManagement: IFraudKnowledgeManagement = sampleWithRequiredData;
        const fraudKnowledgeManagementCollection: IFraudKnowledgeManagement[] = [sampleWithPartialData];
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing(
          fraudKnowledgeManagementCollection,
          fraudKnowledgeManagement
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fraudKnowledgeManagement);
      });

      it('should add only unique FraudKnowledgeManagement to an array', () => {
        const fraudKnowledgeManagementArray: IFraudKnowledgeManagement[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const fraudKnowledgeManagementCollection: IFraudKnowledgeManagement[] = [sampleWithRequiredData];
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing(
          fraudKnowledgeManagementCollection,
          ...fraudKnowledgeManagementArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fraudKnowledgeManagement: IFraudKnowledgeManagement = sampleWithRequiredData;
        const fraudKnowledgeManagement2: IFraudKnowledgeManagement = sampleWithPartialData;
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing([], fraudKnowledgeManagement, fraudKnowledgeManagement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fraudKnowledgeManagement);
        expect(expectedResult).toContain(fraudKnowledgeManagement2);
      });

      it('should accept null and undefined values', () => {
        const fraudKnowledgeManagement: IFraudKnowledgeManagement = sampleWithRequiredData;
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing([], null, fraudKnowledgeManagement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fraudKnowledgeManagement);
      });

      it('should return initial array if no FraudKnowledgeManagement is added', () => {
        const fraudKnowledgeManagementCollection: IFraudKnowledgeManagement[] = [sampleWithRequiredData];
        expectedResult = service.addFraudKnowledgeManagementToCollectionIfMissing(fraudKnowledgeManagementCollection, undefined, null);
        expect(expectedResult).toEqual(fraudKnowledgeManagementCollection);
      });
    });

    describe('compareFraudKnowledgeManagement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFraudKnowledgeManagement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareFraudKnowledgeManagement(entity1, entity2);
        const compareResult2 = service.compareFraudKnowledgeManagement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareFraudKnowledgeManagement(entity1, entity2);
        const compareResult2 = service.compareFraudKnowledgeManagement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareFraudKnowledgeManagement(entity1, entity2);
        const compareResult2 = service.compareFraudKnowledgeManagement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

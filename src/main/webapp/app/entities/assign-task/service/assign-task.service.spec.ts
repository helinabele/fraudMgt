import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAssignTask } from '../assign-task.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../assign-task.test-samples';

import { AssignTaskService, RestAssignTask } from './assign-task.service';

const requireRestSample: RestAssignTask = {
  ...sampleWithRequiredData,
  taskAssignmentDate: sampleWithRequiredData.taskAssignmentDate?.toJSON(),
  taskStartDate: sampleWithRequiredData.taskStartDate?.toJSON(),
  taskEndDate: sampleWithRequiredData.taskEndDate?.toJSON(),
};

describe('AssignTask Service', () => {
  let service: AssignTaskService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssignTask | IAssignTask[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AssignTaskService);
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

    it('should create a AssignTask', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const assignTask = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assignTask).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssignTask', () => {
      const assignTask = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assignTask).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssignTask', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssignTask', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssignTask', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssignTaskToCollectionIfMissing', () => {
      it('should add a AssignTask to an empty array', () => {
        const assignTask: IAssignTask = sampleWithRequiredData;
        expectedResult = service.addAssignTaskToCollectionIfMissing([], assignTask);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assignTask);
      });

      it('should not add a AssignTask to an array that contains it', () => {
        const assignTask: IAssignTask = sampleWithRequiredData;
        const assignTaskCollection: IAssignTask[] = [
          {
            ...assignTask,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssignTaskToCollectionIfMissing(assignTaskCollection, assignTask);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssignTask to an array that doesn't contain it", () => {
        const assignTask: IAssignTask = sampleWithRequiredData;
        const assignTaskCollection: IAssignTask[] = [sampleWithPartialData];
        expectedResult = service.addAssignTaskToCollectionIfMissing(assignTaskCollection, assignTask);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assignTask);
      });

      it('should add only unique AssignTask to an array', () => {
        const assignTaskArray: IAssignTask[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const assignTaskCollection: IAssignTask[] = [sampleWithRequiredData];
        expectedResult = service.addAssignTaskToCollectionIfMissing(assignTaskCollection, ...assignTaskArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assignTask: IAssignTask = sampleWithRequiredData;
        const assignTask2: IAssignTask = sampleWithPartialData;
        expectedResult = service.addAssignTaskToCollectionIfMissing([], assignTask, assignTask2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assignTask);
        expect(expectedResult).toContain(assignTask2);
      });

      it('should accept null and undefined values', () => {
        const assignTask: IAssignTask = sampleWithRequiredData;
        expectedResult = service.addAssignTaskToCollectionIfMissing([], null, assignTask, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assignTask);
      });

      it('should return initial array if no AssignTask is added', () => {
        const assignTaskCollection: IAssignTask[] = [sampleWithRequiredData];
        expectedResult = service.addAssignTaskToCollectionIfMissing(assignTaskCollection, undefined, null);
        expect(expectedResult).toEqual(assignTaskCollection);
      });
    });

    describe('compareAssignTask', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssignTask(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareAssignTask(entity1, entity2);
        const compareResult2 = service.compareAssignTask(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareAssignTask(entity1, entity2);
        const compareResult2 = service.compareAssignTask(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareAssignTask(entity1, entity2);
        const compareResult2 = service.compareAssignTask(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

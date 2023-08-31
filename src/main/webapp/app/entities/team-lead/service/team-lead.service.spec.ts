import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITeamLead } from '../team-lead.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../team-lead.test-samples';

import { TeamLeadService } from './team-lead.service';

const requireRestSample: ITeamLead = {
  ...sampleWithRequiredData,
};

describe('TeamLead Service', () => {
  let service: TeamLeadService;
  let httpMock: HttpTestingController;
  let expectedResult: ITeamLead | ITeamLead[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeamLeadService);
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

    it('should create a TeamLead', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const teamLead = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(teamLead).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TeamLead', () => {
      const teamLead = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(teamLead).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TeamLead', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TeamLead', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TeamLead', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTeamLeadToCollectionIfMissing', () => {
      it('should add a TeamLead to an empty array', () => {
        const teamLead: ITeamLead = sampleWithRequiredData;
        expectedResult = service.addTeamLeadToCollectionIfMissing([], teamLead);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamLead);
      });

      it('should not add a TeamLead to an array that contains it', () => {
        const teamLead: ITeamLead = sampleWithRequiredData;
        const teamLeadCollection: ITeamLead[] = [
          {
            ...teamLead,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTeamLeadToCollectionIfMissing(teamLeadCollection, teamLead);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TeamLead to an array that doesn't contain it", () => {
        const teamLead: ITeamLead = sampleWithRequiredData;
        const teamLeadCollection: ITeamLead[] = [sampleWithPartialData];
        expectedResult = service.addTeamLeadToCollectionIfMissing(teamLeadCollection, teamLead);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamLead);
      });

      it('should add only unique TeamLead to an array', () => {
        const teamLeadArray: ITeamLead[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const teamLeadCollection: ITeamLead[] = [sampleWithRequiredData];
        expectedResult = service.addTeamLeadToCollectionIfMissing(teamLeadCollection, ...teamLeadArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teamLead: ITeamLead = sampleWithRequiredData;
        const teamLead2: ITeamLead = sampleWithPartialData;
        expectedResult = service.addTeamLeadToCollectionIfMissing([], teamLead, teamLead2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teamLead);
        expect(expectedResult).toContain(teamLead2);
      });

      it('should accept null and undefined values', () => {
        const teamLead: ITeamLead = sampleWithRequiredData;
        expectedResult = service.addTeamLeadToCollectionIfMissing([], null, teamLead, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teamLead);
      });

      it('should return initial array if no TeamLead is added', () => {
        const teamLeadCollection: ITeamLead[] = [sampleWithRequiredData];
        expectedResult = service.addTeamLeadToCollectionIfMissing(teamLeadCollection, undefined, null);
        expect(expectedResult).toEqual(teamLeadCollection);
      });
    });

    describe('compareTeamLead', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTeamLead(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareTeamLead(entity1, entity2);
        const compareResult2 = service.compareTeamLead(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareTeamLead(entity1, entity2);
        const compareResult2 = service.compareTeamLead(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareTeamLead(entity1, entity2);
        const compareResult2 = service.compareTeamLead(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

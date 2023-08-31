import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../team-lead.test-samples';

import { TeamLeadFormService } from './team-lead-form.service';

describe('TeamLead Form Service', () => {
  let service: TeamLeadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamLeadFormService);
  });

  describe('Service methods', () => {
    describe('createTeamLeadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTeamLeadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            teamLeadName: expect.any(Object),
            description: expect.any(Object),
            managerialId: expect.any(Object),
            user: expect.any(Object),
            managers: expect.any(Object),
          })
        );
      });

      it('passing ITeamLead should create a new form with FormGroup', () => {
        const formGroup = service.createTeamLeadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            teamLeadName: expect.any(Object),
            description: expect.any(Object),
            managerialId: expect.any(Object),
            user: expect.any(Object),
            managers: expect.any(Object),
          })
        );
      });
    });

    describe('getTeamLead', () => {
      it('should return NewTeamLead for default TeamLead initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTeamLeadFormGroup(sampleWithNewData);

        const teamLead = service.getTeamLead(formGroup) as any;

        expect(teamLead).toMatchObject(sampleWithNewData);
      });

      it('should return NewTeamLead for empty TeamLead initial value', () => {
        const formGroup = service.createTeamLeadFormGroup();

        const teamLead = service.getTeamLead(formGroup) as any;

        expect(teamLead).toMatchObject({});
      });

      it('should return ITeamLead', () => {
        const formGroup = service.createTeamLeadFormGroup(sampleWithRequiredData);

        const teamLead = service.getTeamLead(formGroup) as any;

        expect(teamLead).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITeamLead should not enable id FormControl', () => {
        const formGroup = service.createTeamLeadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTeamLead should disable id FormControl', () => {
        const formGroup = service.createTeamLeadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

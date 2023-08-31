import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../findings.test-samples';

import { FindingsFormService } from './findings-form.service';

describe('Findings Form Service', () => {
  let service: FindingsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FindingsFormService);
  });

  describe('Service methods', () => {
    describe('createFindingsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFindingsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            findingAndAnalysis: expect.any(Object),
            findingAndAnalysisAnnex: expect.any(Object),
            fraudInvestigationReport: expect.any(Object),
          })
        );
      });

      it('passing IFindings should create a new form with FormGroup', () => {
        const formGroup = service.createFindingsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            findingAndAnalysis: expect.any(Object),
            findingAndAnalysisAnnex: expect.any(Object),
            fraudInvestigationReport: expect.any(Object),
          })
        );
      });
    });

    describe('getFindings', () => {
      it('should return NewFindings for default Findings initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFindingsFormGroup(sampleWithNewData);

        const findings = service.getFindings(formGroup) as any;

        expect(findings).toMatchObject(sampleWithNewData);
      });

      it('should return NewFindings for empty Findings initial value', () => {
        const formGroup = service.createFindingsFormGroup();

        const findings = service.getFindings(formGroup) as any;

        expect(findings).toMatchObject({});
      });

      it('should return IFindings', () => {
        const formGroup = service.createFindingsFormGroup(sampleWithRequiredData);

        const findings = service.getFindings(formGroup) as any;

        expect(findings).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFindings should not enable id FormControl', () => {
        const formGroup = service.createFindingsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFindings should disable id FormControl', () => {
        const formGroup = service.createFindingsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

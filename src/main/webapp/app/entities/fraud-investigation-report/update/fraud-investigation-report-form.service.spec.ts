import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fraud-investigation-report.test-samples';

import { FraudInvestigationReportFormService } from './fraud-investigation-report-form.service';

describe('FraudInvestigationReport Form Service', () => {
  let service: FraudInvestigationReportFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FraudInvestigationReportFormService);
  });

  describe('Service methods', () => {
    describe('createFraudInvestigationReportFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFraudInvestigationReportFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            executiveSummary: expect.any(Object),
            introductionAnnex: expect.any(Object),
            introduction: expect.any(Object),
            objective: expect.any(Object),
            objectiveAnnex: expect.any(Object),
            scope: expect.any(Object),
            scopeAnnex: expect.any(Object),
            limitation: expect.any(Object),
            limitationAnnex: expect.any(Object),
            methodology: expect.any(Object),
            methodologyAnnex: expect.any(Object),
            findingAndAnalysis: expect.any(Object),
            findingAndAnalysisAnnex: expect.any(Object),
            conclusion: expect.any(Object),
            conclusionAnnex: expect.any(Object),
            recommendation: expect.any(Object),
            recommendationAnnex: expect.any(Object),
            nameOfMembers: expect.any(Object),
            signature: expect.any(Object),
            references: expect.any(Object),
            publicationDate: expect.any(Object),
            author: expect.any(Object),
            employee: expect.any(Object),
            task: expect.any(Object),
            team: expect.any(Object),
          })
        );
      });

      it('passing IFraudInvestigationReport should create a new form with FormGroup', () => {
        const formGroup = service.createFraudInvestigationReportFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            executiveSummary: expect.any(Object),
            introductionAnnex: expect.any(Object),
            introduction: expect.any(Object),
            objective: expect.any(Object),
            objectiveAnnex: expect.any(Object),
            scope: expect.any(Object),
            scopeAnnex: expect.any(Object),
            limitation: expect.any(Object),
            limitationAnnex: expect.any(Object),
            methodology: expect.any(Object),
            methodologyAnnex: expect.any(Object),
            findingAndAnalysis: expect.any(Object),
            findingAndAnalysisAnnex: expect.any(Object),
            conclusion: expect.any(Object),
            conclusionAnnex: expect.any(Object),
            recommendation: expect.any(Object),
            recommendationAnnex: expect.any(Object),
            nameOfMembers: expect.any(Object),
            signature: expect.any(Object),
            references: expect.any(Object),
            publicationDate: expect.any(Object),
            author: expect.any(Object),
            employee: expect.any(Object),
            task: expect.any(Object),
            team: expect.any(Object),
          })
        );
      });
    });

    describe('getFraudInvestigationReport', () => {
      it('should return NewFraudInvestigationReport for default FraudInvestigationReport initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFraudInvestigationReportFormGroup(sampleWithNewData);

        const fraudInvestigationReport = service.getFraudInvestigationReport(formGroup) as any;

        expect(fraudInvestigationReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewFraudInvestigationReport for empty FraudInvestigationReport initial value', () => {
        const formGroup = service.createFraudInvestigationReportFormGroup();

        const fraudInvestigationReport = service.getFraudInvestigationReport(formGroup) as any;

        expect(fraudInvestigationReport).toMatchObject({});
      });

      it('should return IFraudInvestigationReport', () => {
        const formGroup = service.createFraudInvestigationReportFormGroup(sampleWithRequiredData);

        const fraudInvestigationReport = service.getFraudInvestigationReport(formGroup) as any;

        expect(fraudInvestigationReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFraudInvestigationReport should not enable id FormControl', () => {
        const formGroup = service.createFraudInvestigationReportFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFraudInvestigationReport should disable id FormControl', () => {
        const formGroup = service.createFraudInvestigationReportFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

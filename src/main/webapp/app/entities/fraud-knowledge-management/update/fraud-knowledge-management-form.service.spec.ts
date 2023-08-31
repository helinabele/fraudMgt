import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fraud-knowledge-management.test-samples';

import { FraudKnowledgeManagementFormService } from './fraud-knowledge-management-form.service';

describe('FraudKnowledgeManagement Form Service', () => {
  let service: FraudKnowledgeManagementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FraudKnowledgeManagementFormService);
  });

  describe('Service methods', () => {
    describe('createFraudKnowledgeManagementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFraudKnowledgeManagementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reportNumber: expect.any(Object),
            fraudIncident: expect.any(Object),
            actualIncident: expect.any(Object),
            attemptIncident: expect.any(Object),
            reasonForFailure: expect.any(Object),
            unit: expect.any(Object),
            incidentDate: expect.any(Object),
            dateOfDetection: expect.any(Object),
            reasonForDelay: expect.any(Object),
            projectCreationDate: expect.any(Object),
            reportDate: expect.any(Object),
            suspectedFraudster: expect.any(Object),
            financialLossAmount: expect.any(Object),
            actualFraudAmount: expect.any(Object),
            debitAccount: expect.any(Object),
            creditAccount: expect.any(Object),
            techniquesandTechnologiesUsed: expect.any(Object),
            causeForAnIncident: expect.any(Object),
            systemAndProceduralLoophole: expect.any(Object),
            effect: expect.any(Object),
            recommendationsDrawn: expect.any(Object),
            positionJG: expect.any(Object),
            nameIdNo: expect.any(Object),
            actionInvolved: expect.any(Object),
            ngScreenerReport: expect.any(Object),
            committeeDecision: expect.any(Object),
            measureTaken: expect.any(Object),
            fraudAmountRecovered: expect.any(Object),
            fraudAmountWrittenOff: expect.any(Object),
            previouslyHeldForFraudOutstanding: expect.any(Object),
            employee: expect.any(Object),
            fraudInvestigationReport: expect.any(Object),
            fraudType: expect.any(Object),
            bankAccount: expect.any(Object),
            bankService: expect.any(Object),
            internalEmployee: expect.any(Object),
            externalEmployee: expect.any(Object),
          })
        );
      });

      it('passing IFraudKnowledgeManagement should create a new form with FormGroup', () => {
        const formGroup = service.createFraudKnowledgeManagementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reportNumber: expect.any(Object),
            fraudIncident: expect.any(Object),
            actualIncident: expect.any(Object),
            attemptIncident: expect.any(Object),
            reasonForFailure: expect.any(Object),
            unit: expect.any(Object),
            incidentDate: expect.any(Object),
            dateOfDetection: expect.any(Object),
            reasonForDelay: expect.any(Object),
            projectCreationDate: expect.any(Object),
            reportDate: expect.any(Object),
            suspectedFraudster: expect.any(Object),
            financialLossAmount: expect.any(Object),
            actualFraudAmount: expect.any(Object),
            debitAccount: expect.any(Object),
            creditAccount: expect.any(Object),
            techniquesandTechnologiesUsed: expect.any(Object),
            causeForAnIncident: expect.any(Object),
            systemAndProceduralLoophole: expect.any(Object),
            effect: expect.any(Object),
            recommendationsDrawn: expect.any(Object),
            positionJG: expect.any(Object),
            nameIdNo: expect.any(Object),
            actionInvolved: expect.any(Object),
            ngScreenerReport: expect.any(Object),
            committeeDecision: expect.any(Object),
            measureTaken: expect.any(Object),
            fraudAmountRecovered: expect.any(Object),
            fraudAmountWrittenOff: expect.any(Object),
            previouslyHeldForFraudOutstanding: expect.any(Object),
            employee: expect.any(Object),
            fraudInvestigationReport: expect.any(Object),
            fraudType: expect.any(Object),
            bankAccount: expect.any(Object),
            bankService: expect.any(Object),
            internalEmployee: expect.any(Object),
            externalEmployee: expect.any(Object),
          })
        );
      });
    });

    describe('getFraudKnowledgeManagement', () => {
      it('should return NewFraudKnowledgeManagement for default FraudKnowledgeManagement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFraudKnowledgeManagementFormGroup(sampleWithNewData);

        const fraudKnowledgeManagement = service.getFraudKnowledgeManagement(formGroup) as any;

        expect(fraudKnowledgeManagement).toMatchObject(sampleWithNewData);
      });

      it('should return NewFraudKnowledgeManagement for empty FraudKnowledgeManagement initial value', () => {
        const formGroup = service.createFraudKnowledgeManagementFormGroup();

        const fraudKnowledgeManagement = service.getFraudKnowledgeManagement(formGroup) as any;

        expect(fraudKnowledgeManagement).toMatchObject({});
      });

      it('should return IFraudKnowledgeManagement', () => {
        const formGroup = service.createFraudKnowledgeManagementFormGroup(sampleWithRequiredData);

        const fraudKnowledgeManagement = service.getFraudKnowledgeManagement(formGroup) as any;

        expect(fraudKnowledgeManagement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFraudKnowledgeManagement should not enable id FormControl', () => {
        const formGroup = service.createFraudKnowledgeManagementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFraudKnowledgeManagement should disable id FormControl', () => {
        const formGroup = service.createFraudKnowledgeManagementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

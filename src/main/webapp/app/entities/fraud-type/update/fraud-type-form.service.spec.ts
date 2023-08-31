import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fraud-type.test-samples';

import { FraudTypeFormService } from './fraud-type-form.service';

describe('FraudType Form Service', () => {
  let service: FraudTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FraudTypeFormService);
  });

  describe('Service methods', () => {
    describe('createFraudTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFraudTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fraudName: expect.any(Object),
            description: expect.any(Object),
            attachment: expect.any(Object),
          })
        );
      });

      it('passing IFraudType should create a new form with FormGroup', () => {
        const formGroup = service.createFraudTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fraudName: expect.any(Object),
            description: expect.any(Object),
            attachment: expect.any(Object),
          })
        );
      });
    });

    describe('getFraudType', () => {
      it('should return NewFraudType for default FraudType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFraudTypeFormGroup(sampleWithNewData);

        const fraudType = service.getFraudType(formGroup) as any;

        expect(fraudType).toMatchObject(sampleWithNewData);
      });

      it('should return NewFraudType for empty FraudType initial value', () => {
        const formGroup = service.createFraudTypeFormGroup();

        const fraudType = service.getFraudType(formGroup) as any;

        expect(fraudType).toMatchObject({});
      });

      it('should return IFraudType', () => {
        const formGroup = service.createFraudTypeFormGroup(sampleWithRequiredData);

        const fraudType = service.getFraudType(formGroup) as any;

        expect(fraudType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFraudType should not enable id FormControl', () => {
        const formGroup = service.createFraudTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFraudType should disable id FormControl', () => {
        const formGroup = service.createFraudTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

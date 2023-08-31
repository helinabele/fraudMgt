import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bank-service.test-samples';

import { BankServiceFormService } from './bank-service-form.service';

describe('BankService Form Service', () => {
  let service: BankServiceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankServiceFormService);
  });

  describe('Service methods', () => {
    describe('createBankServiceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBankServiceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            serviceName: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IBankService should create a new form with FormGroup', () => {
        const formGroup = service.createBankServiceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            serviceName: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getBankService', () => {
      it('should return NewBankService for default BankService initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBankServiceFormGroup(sampleWithNewData);

        const bankService = service.getBankService(formGroup) as any;

        expect(bankService).toMatchObject(sampleWithNewData);
      });

      it('should return NewBankService for empty BankService initial value', () => {
        const formGroup = service.createBankServiceFormGroup();

        const bankService = service.getBankService(formGroup) as any;

        expect(bankService).toMatchObject({});
      });

      it('should return IBankService', () => {
        const formGroup = service.createBankServiceFormGroup(sampleWithRequiredData);

        const bankService = service.getBankService(formGroup) as any;

        expect(bankService).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBankService should not enable id FormControl', () => {
        const formGroup = service.createBankServiceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBankService should disable id FormControl', () => {
        const formGroup = service.createBankServiceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

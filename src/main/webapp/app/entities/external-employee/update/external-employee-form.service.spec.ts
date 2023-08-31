import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../external-employee.test-samples';

import { ExternalEmployeeFormService } from './external-employee-form.service';

describe('ExternalEmployee Form Service', () => {
  let service: ExternalEmployeeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExternalEmployeeFormService);
  });

  describe('Service methods', () => {
    describe('createExternalEmployeeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExternalEmployeeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            occupation: expect.any(Object),
            telephone: expect.any(Object),
            address: expect.any(Object),
          })
        );
      });

      it('passing IExternalEmployee should create a new form with FormGroup', () => {
        const formGroup = service.createExternalEmployeeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            occupation: expect.any(Object),
            telephone: expect.any(Object),
            address: expect.any(Object),
          })
        );
      });
    });

    describe('getExternalEmployee', () => {
      it('should return NewExternalEmployee for default ExternalEmployee initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createExternalEmployeeFormGroup(sampleWithNewData);

        const externalEmployee = service.getExternalEmployee(formGroup) as any;

        expect(externalEmployee).toMatchObject(sampleWithNewData);
      });

      it('should return NewExternalEmployee for empty ExternalEmployee initial value', () => {
        const formGroup = service.createExternalEmployeeFormGroup();

        const externalEmployee = service.getExternalEmployee(formGroup) as any;

        expect(externalEmployee).toMatchObject({});
      });

      it('should return IExternalEmployee', () => {
        const formGroup = service.createExternalEmployeeFormGroup(sampleWithRequiredData);

        const externalEmployee = service.getExternalEmployee(formGroup) as any;

        expect(externalEmployee).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExternalEmployee should not enable id FormControl', () => {
        const formGroup = service.createExternalEmployeeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExternalEmployee should disable id FormControl', () => {
        const formGroup = service.createExternalEmployeeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

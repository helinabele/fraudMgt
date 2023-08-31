import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../internal-employee.test-samples';

import { InternalEmployeeFormService } from './internal-employee-form.service';

describe('InternalEmployee Form Service', () => {
  let service: InternalEmployeeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternalEmployeeFormService);
  });

  describe('Service methods', () => {
    describe('createInternalEmployeeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInternalEmployeeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            position: expect.any(Object),
            grade: expect.any(Object),
            experience: expect.any(Object),
            branch: expect.any(Object),
          })
        );
      });

      it('passing IInternalEmployee should create a new form with FormGroup', () => {
        const formGroup = service.createInternalEmployeeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            position: expect.any(Object),
            grade: expect.any(Object),
            experience: expect.any(Object),
            branch: expect.any(Object),
          })
        );
      });
    });

    describe('getInternalEmployee', () => {
      it('should return NewInternalEmployee for default InternalEmployee initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInternalEmployeeFormGroup(sampleWithNewData);

        const internalEmployee = service.getInternalEmployee(formGroup) as any;

        expect(internalEmployee).toMatchObject(sampleWithNewData);
      });

      it('should return NewInternalEmployee for empty InternalEmployee initial value', () => {
        const formGroup = service.createInternalEmployeeFormGroup();

        const internalEmployee = service.getInternalEmployee(formGroup) as any;

        expect(internalEmployee).toMatchObject({});
      });

      it('should return IInternalEmployee', () => {
        const formGroup = service.createInternalEmployeeFormGroup(sampleWithRequiredData);

        const internalEmployee = service.getInternalEmployee(formGroup) as any;

        expect(internalEmployee).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInternalEmployee should not enable id FormControl', () => {
        const formGroup = service.createInternalEmployeeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInternalEmployee should disable id FormControl', () => {
        const formGroup = service.createInternalEmployeeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

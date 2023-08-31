import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../managerial.test-samples';

import { ManagerialFormService } from './managerial-form.service';

describe('Managerial Form Service', () => {
  let service: ManagerialFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManagerialFormService);
  });

  describe('Service methods', () => {
    describe('createManagerialFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createManagerialFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            managerialName: expect.any(Object),
            description: expect.any(Object),
            directorId: expect.any(Object),
            user: expect.any(Object),
            directors: expect.any(Object),
          })
        );
      });

      it('passing IManagerial should create a new form with FormGroup', () => {
        const formGroup = service.createManagerialFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            managerialName: expect.any(Object),
            description: expect.any(Object),
            directorId: expect.any(Object),
            user: expect.any(Object),
            directors: expect.any(Object),
          })
        );
      });
    });

    describe('getManagerial', () => {
      it('should return NewManagerial for default Managerial initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createManagerialFormGroup(sampleWithNewData);

        const managerial = service.getManagerial(formGroup) as any;

        expect(managerial).toMatchObject(sampleWithNewData);
      });

      it('should return NewManagerial for empty Managerial initial value', () => {
        const formGroup = service.createManagerialFormGroup();

        const managerial = service.getManagerial(formGroup) as any;

        expect(managerial).toMatchObject({});
      });

      it('should return IManagerial', () => {
        const formGroup = service.createManagerialFormGroup(sampleWithRequiredData);

        const managerial = service.getManagerial(formGroup) as any;

        expect(managerial).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IManagerial should not enable id FormControl', () => {
        const formGroup = service.createManagerialFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewManagerial should disable id FormControl', () => {
        const formGroup = service.createManagerialFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

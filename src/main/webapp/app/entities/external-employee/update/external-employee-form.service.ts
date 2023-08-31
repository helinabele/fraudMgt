import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IExternalEmployee, NewExternalEmployee } from '../external-employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExternalEmployee for edit and NewExternalEmployeeFormGroupInput for create.
 */
type ExternalEmployeeFormGroupInput = IExternalEmployee | PartialWithRequiredKeyOf<NewExternalEmployee>;

type ExternalEmployeeFormDefaults = Pick<NewExternalEmployee, 'id'>;

type ExternalEmployeeFormGroupContent = {
  id: FormControl<IExternalEmployee['id'] | NewExternalEmployee['id']>;
  name: FormControl<IExternalEmployee['name']>;
  occupation: FormControl<IExternalEmployee['occupation']>;
  telephone: FormControl<IExternalEmployee['telephone']>;
  address: FormControl<IExternalEmployee['address']>;
};

export type ExternalEmployeeFormGroup = FormGroup<ExternalEmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExternalEmployeeFormService {
  createExternalEmployeeFormGroup(externalEmployee: ExternalEmployeeFormGroupInput = { id: null }): ExternalEmployeeFormGroup {
    const externalEmployeeRawValue = {
      ...this.getFormDefaults(),
      ...externalEmployee,
    };
    return new FormGroup<ExternalEmployeeFormGroupContent>({
      id: new FormControl(
        { value: externalEmployeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(externalEmployeeRawValue.name, {
        validators: [Validators.required],
      }),
      occupation: new FormControl(externalEmployeeRawValue.occupation),
      telephone: new FormControl(externalEmployeeRawValue.telephone),
      address: new FormControl(externalEmployeeRawValue.address, {
        validators: [Validators.required],
      }),
    });
  }

  getExternalEmployee(form: ExternalEmployeeFormGroup): IExternalEmployee | NewExternalEmployee {
    return form.getRawValue() as IExternalEmployee | NewExternalEmployee;
  }

  resetForm(form: ExternalEmployeeFormGroup, externalEmployee: ExternalEmployeeFormGroupInput): void {
    const externalEmployeeRawValue = { ...this.getFormDefaults(), ...externalEmployee };
    form.reset(
      {
        ...externalEmployeeRawValue,
        id: { value: externalEmployeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ExternalEmployeeFormDefaults {
    return {
      id: null,
    };
  }
}

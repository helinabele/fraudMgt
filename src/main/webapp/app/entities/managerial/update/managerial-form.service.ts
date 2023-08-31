import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IManagerial, NewManagerial } from '../managerial.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IManagerial for edit and NewManagerialFormGroupInput for create.
 */
type ManagerialFormGroupInput = IManagerial | PartialWithRequiredKeyOf<NewManagerial>;

type ManagerialFormDefaults = Pick<NewManagerial, 'id'>;

type ManagerialFormGroupContent = {
  id: FormControl<IManagerial['id'] | NewManagerial['id']>;
  managerialName: FormControl<IManagerial['managerialName']>;
  description: FormControl<IManagerial['description']>;
  directorId: FormControl<IManagerial['directorId']>;
  user: FormControl<IManagerial['user']>;
  directors: FormControl<IManagerial['directors']>;
};

export type ManagerialFormGroup = FormGroup<ManagerialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ManagerialFormService {
  createManagerialFormGroup(managerial: ManagerialFormGroupInput = { id: null }): ManagerialFormGroup {
    const managerialRawValue = {
      ...this.getFormDefaults(),
      ...managerial,
    };
    return new FormGroup<ManagerialFormGroupContent>({
      id: new FormControl(
        { value: managerialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      managerialName: new FormControl(managerialRawValue.managerialName, {
        validators: [Validators.maxLength(50)],
      }),
      description: new FormControl(managerialRawValue.description),
      directorId: new FormControl(managerialRawValue.directorId),
      user: new FormControl(managerialRawValue.user),
      directors: new FormControl(managerialRawValue.directors),
    });
  }

  getManagerial(form: ManagerialFormGroup): IManagerial | NewManagerial {
    return form.getRawValue() as IManagerial | NewManagerial;
  }

  resetForm(form: ManagerialFormGroup, managerial: ManagerialFormGroupInput): void {
    const managerialRawValue = { ...this.getFormDefaults(), ...managerial };
    form.reset(
      {
        ...managerialRawValue,
        id: { value: managerialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ManagerialFormDefaults {
    return {
      id: null,
    };
  }
}

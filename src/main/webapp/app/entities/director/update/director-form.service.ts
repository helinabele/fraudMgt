import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDirector, NewDirector } from '../director.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDirector for edit and NewDirectorFormGroupInput for create.
 */
type DirectorFormGroupInput = IDirector | PartialWithRequiredKeyOf<NewDirector>;

type DirectorFormDefaults = Pick<NewDirector, 'id'>;

type DirectorFormGroupContent = {
  id: FormControl<IDirector['id'] | NewDirector['id']>;
  directorName: FormControl<IDirector['directorName']>;
  description: FormControl<IDirector['description']>;
  user: FormControl<IDirector['user']>;
};

export type DirectorFormGroup = FormGroup<DirectorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DirectorFormService {
  createDirectorFormGroup(director: DirectorFormGroupInput = { id: null }): DirectorFormGroup {
    const directorRawValue = {
      ...this.getFormDefaults(),
      ...director,
    };
    return new FormGroup<DirectorFormGroupContent>({
      id: new FormControl(
        { value: directorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      directorName: new FormControl(directorRawValue.directorName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      description: new FormControl(directorRawValue.description),
      user: new FormControl(directorRawValue.user),
    });
  }

  getDirector(form: DirectorFormGroup): IDirector | NewDirector {
    return form.getRawValue() as IDirector | NewDirector;
  }

  resetForm(form: DirectorFormGroup, director: DirectorFormGroupInput): void {
    const directorRawValue = { ...this.getFormDefaults(), ...director };
    form.reset(
      {
        ...directorRawValue,
        id: { value: directorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DirectorFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFraudType, NewFraudType } from '../fraud-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFraudType for edit and NewFraudTypeFormGroupInput for create.
 */
type FraudTypeFormGroupInput = IFraudType | PartialWithRequiredKeyOf<NewFraudType>;

type FraudTypeFormDefaults = Pick<NewFraudType, 'id'>;

type FraudTypeFormGroupContent = {
  id: FormControl<IFraudType['id'] | NewFraudType['id']>;
  fraudName: FormControl<IFraudType['fraudName']>;
  description: FormControl<IFraudType['description']>;
  attachment: FormControl<IFraudType['attachment']>;
  attachmentContentType: FormControl<IFraudType['attachmentContentType']>;
};

export type FraudTypeFormGroup = FormGroup<FraudTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FraudTypeFormService {
  createFraudTypeFormGroup(fraudType: FraudTypeFormGroupInput = { id: null }): FraudTypeFormGroup {
    const fraudTypeRawValue = {
      ...this.getFormDefaults(),
      ...fraudType,
    };
    return new FormGroup<FraudTypeFormGroupContent>({
      id: new FormControl(
        { value: fraudTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fraudName: new FormControl(fraudTypeRawValue.fraudName),
      description: new FormControl(fraudTypeRawValue.description),
      attachment: new FormControl(fraudTypeRawValue.attachment),
      attachmentContentType: new FormControl(fraudTypeRawValue.attachmentContentType),
    });
  }

  getFraudType(form: FraudTypeFormGroup): IFraudType | NewFraudType {
    return form.getRawValue() as IFraudType | NewFraudType;
  }

  resetForm(form: FraudTypeFormGroup, fraudType: FraudTypeFormGroupInput): void {
    const fraudTypeRawValue = { ...this.getFormDefaults(), ...fraudType };
    form.reset(
      {
        ...fraudTypeRawValue,
        id: { value: fraudTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FraudTypeFormDefaults {
    return {
      id: null,
    };
  }
}

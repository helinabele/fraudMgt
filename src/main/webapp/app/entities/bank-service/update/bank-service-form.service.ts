import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBankService, NewBankService } from '../bank-service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBankService for edit and NewBankServiceFormGroupInput for create.
 */
type BankServiceFormGroupInput = IBankService | PartialWithRequiredKeyOf<NewBankService>;

type BankServiceFormDefaults = Pick<NewBankService, 'id'>;

type BankServiceFormGroupContent = {
  id: FormControl<IBankService['id'] | NewBankService['id']>;
  serviceName: FormControl<IBankService['serviceName']>;
  description: FormControl<IBankService['description']>;
};

export type BankServiceFormGroup = FormGroup<BankServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BankServiceFormService {
  createBankServiceFormGroup(bankService: BankServiceFormGroupInput = { id: null }): BankServiceFormGroup {
    const bankServiceRawValue = {
      ...this.getFormDefaults(),
      ...bankService,
    };
    return new FormGroup<BankServiceFormGroupContent>({
      id: new FormControl(
        { value: bankServiceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      serviceName: new FormControl(bankServiceRawValue.serviceName, {
        validators: [Validators.required],
      }),
      description: new FormControl(bankServiceRawValue.description),
    });
  }

  getBankService(form: BankServiceFormGroup): IBankService | NewBankService {
    return form.getRawValue() as IBankService | NewBankService;
  }

  resetForm(form: BankServiceFormGroup, bankService: BankServiceFormGroupInput): void {
    const bankServiceRawValue = { ...this.getFormDefaults(), ...bankService };
    form.reset(
      {
        ...bankServiceRawValue,
        id: { value: bankServiceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BankServiceFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IWhistleBlowerReport, NewWhistleBlowerReport } from '../whistle-blower-report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWhistleBlowerReport for edit and NewWhistleBlowerReportFormGroupInput for create.
 */
type WhistleBlowerReportFormGroupInput = IWhistleBlowerReport | PartialWithRequiredKeyOf<NewWhistleBlowerReport>;

type WhistleBlowerReportFormDefaults = Pick<NewWhistleBlowerReport, 'id'>;

type WhistleBlowerReportFormGroupContent = {
  id: FormControl<IWhistleBlowerReport['id'] | NewWhistleBlowerReport['id']>;
  fullName: FormControl<IWhistleBlowerReport['fullName']>;
  genderType: FormControl<IWhistleBlowerReport['genderType']>;
  emailAdress: FormControl<IWhistleBlowerReport['emailAdress']>;
  phone: FormControl<IWhistleBlowerReport['phone']>;
  organization: FormControl<IWhistleBlowerReport['organization']>;
  message: FormControl<IWhistleBlowerReport['message']>;
  attachment: FormControl<IWhistleBlowerReport['attachment']>;
  attachmentContentType: FormControl<IWhistleBlowerReport['attachmentContentType']>;
};

export type WhistleBlowerReportFormGroup = FormGroup<WhistleBlowerReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WhistleBlowerReportFormService {
  createWhistleBlowerReportFormGroup(whistleBlowerReport: WhistleBlowerReportFormGroupInput = { id: null }): WhistleBlowerReportFormGroup {
    const whistleBlowerReportRawValue = {
      ...this.getFormDefaults(),
      ...whistleBlowerReport,
    };
    return new FormGroup<WhistleBlowerReportFormGroupContent>({
      id: new FormControl(
        { value: whistleBlowerReportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fullName: new FormControl(whistleBlowerReportRawValue.fullName),
      genderType: new FormControl(whistleBlowerReportRawValue.genderType),
      emailAdress: new FormControl(whistleBlowerReportRawValue.emailAdress),
      phone: new FormControl(whistleBlowerReportRawValue.phone),
      organization: new FormControl(whistleBlowerReportRawValue.organization),
      message: new FormControl(whistleBlowerReportRawValue.message),
      attachment: new FormControl(whistleBlowerReportRawValue.attachment),
      attachmentContentType: new FormControl(whistleBlowerReportRawValue.attachmentContentType),
    });
  }

  getWhistleBlowerReport(form: WhistleBlowerReportFormGroup): IWhistleBlowerReport | NewWhistleBlowerReport {
    return form.getRawValue() as IWhistleBlowerReport | NewWhistleBlowerReport;
  }

  resetForm(form: WhistleBlowerReportFormGroup, whistleBlowerReport: WhistleBlowerReportFormGroupInput): void {
    const whistleBlowerReportRawValue = { ...this.getFormDefaults(), ...whistleBlowerReport };
    form.reset(
      {
        ...whistleBlowerReportRawValue,
        id: { value: whistleBlowerReportRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WhistleBlowerReportFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFindings, NewFindings } from '../findings.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFindings for edit and NewFindingsFormGroupInput for create.
 */
type FindingsFormGroupInput = IFindings | PartialWithRequiredKeyOf<NewFindings>;

type FindingsFormDefaults = Pick<NewFindings, 'id'>;

type FindingsFormGroupContent = {
  id: FormControl<IFindings['id'] | NewFindings['id']>;
  findingAndAnalysis: FormControl<IFindings['findingAndAnalysis']>;
  findingAndAnalysisAnnex: FormControl<IFindings['findingAndAnalysisAnnex']>;
  findingAndAnalysisAnnexContentType: FormControl<IFindings['findingAndAnalysisAnnexContentType']>;
  fraudInvestigationReport: FormControl<IFindings['fraudInvestigationReport']>;
};

export type FindingsFormGroup = FormGroup<FindingsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FindingsFormService {
  createFindingsFormGroup(findings: FindingsFormGroupInput = { id: null }): FindingsFormGroup {
    const findingsRawValue = {
      ...this.getFormDefaults(),
      ...findings,
    };
    return new FormGroup<FindingsFormGroupContent>({
      id: new FormControl(
        { value: findingsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      findingAndAnalysis: new FormControl(findingsRawValue.findingAndAnalysis),
      findingAndAnalysisAnnex: new FormControl(findingsRawValue.findingAndAnalysisAnnex),
      findingAndAnalysisAnnexContentType: new FormControl(findingsRawValue.findingAndAnalysisAnnexContentType),
      fraudInvestigationReport: new FormControl(findingsRawValue.fraudInvestigationReport),
    });
  }

  getFindings(form: FindingsFormGroup): IFindings | NewFindings {
    return form.getRawValue() as IFindings | NewFindings;
  }

  resetForm(form: FindingsFormGroup, findings: FindingsFormGroupInput): void {
    const findingsRawValue = { ...this.getFormDefaults(), ...findings };
    form.reset(
      {
        ...findingsRawValue,
        id: { value: findingsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FindingsFormDefaults {
    return {
      id: null,
    };
  }
}

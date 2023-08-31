import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFraudKnowledgeManagement, NewFraudKnowledgeManagement } from '../fraud-knowledge-management.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFraudKnowledgeManagement for edit and NewFraudKnowledgeManagementFormGroupInput for create.
 */
type FraudKnowledgeManagementFormGroupInput = IFraudKnowledgeManagement | PartialWithRequiredKeyOf<NewFraudKnowledgeManagement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFraudKnowledgeManagement | NewFraudKnowledgeManagement> = Omit<T, 'incidentDate' | 'dateOfDetection'> & {
  incidentDate?: string | null;
  dateOfDetection?: string | null;
};

type FraudKnowledgeManagementFormRawValue = FormValueOf<IFraudKnowledgeManagement>;

type NewFraudKnowledgeManagementFormRawValue = FormValueOf<NewFraudKnowledgeManagement>;

type FraudKnowledgeManagementFormDefaults = Pick<NewFraudKnowledgeManagement, 'id' | 'incidentDate' | 'dateOfDetection'>;

type FraudKnowledgeManagementFormGroupContent = {
  id: FormControl<FraudKnowledgeManagementFormRawValue['id'] | NewFraudKnowledgeManagement['id']>;
  reportNumber: FormControl<FraudKnowledgeManagementFormRawValue['reportNumber']>;
  fraudIncident: FormControl<FraudKnowledgeManagementFormRawValue['fraudIncident']>;
  actualIncident: FormControl<FraudKnowledgeManagementFormRawValue['actualIncident']>;
  attemptIncident: FormControl<FraudKnowledgeManagementFormRawValue['attemptIncident']>;
  reasonForFailure: FormControl<FraudKnowledgeManagementFormRawValue['reasonForFailure']>;
  unit: FormControl<FraudKnowledgeManagementFormRawValue['unit']>;
  incidentDate: FormControl<FraudKnowledgeManagementFormRawValue['incidentDate']>;
  dateOfDetection: FormControl<FraudKnowledgeManagementFormRawValue['dateOfDetection']>;
  reasonForDelay: FormControl<FraudKnowledgeManagementFormRawValue['reasonForDelay']>;
  projectCreationDate: FormControl<FraudKnowledgeManagementFormRawValue['projectCreationDate']>;
  reportDate: FormControl<FraudKnowledgeManagementFormRawValue['reportDate']>;
  suspectedFraudster: FormControl<FraudKnowledgeManagementFormRawValue['suspectedFraudster']>;
  financialLossAmount: FormControl<FraudKnowledgeManagementFormRawValue['financialLossAmount']>;
  actualFraudAmount: FormControl<FraudKnowledgeManagementFormRawValue['actualFraudAmount']>;
  debitAccount: FormControl<FraudKnowledgeManagementFormRawValue['debitAccount']>;
  creditAccount: FormControl<FraudKnowledgeManagementFormRawValue['creditAccount']>;
  techniquesandTechnologiesUsed: FormControl<FraudKnowledgeManagementFormRawValue['techniquesandTechnologiesUsed']>;
  causeForAnIncident: FormControl<FraudKnowledgeManagementFormRawValue['causeForAnIncident']>;
  systemAndProceduralLoophole: FormControl<FraudKnowledgeManagementFormRawValue['systemAndProceduralLoophole']>;
  effect: FormControl<FraudKnowledgeManagementFormRawValue['effect']>;
  recommendationsDrawn: FormControl<FraudKnowledgeManagementFormRawValue['recommendationsDrawn']>;
  positionJG: FormControl<FraudKnowledgeManagementFormRawValue['positionJG']>;
  nameIdNo: FormControl<FraudKnowledgeManagementFormRawValue['nameIdNo']>;
  actionInvolved: FormControl<FraudKnowledgeManagementFormRawValue['actionInvolved']>;
  ngScreenerReport: FormControl<FraudKnowledgeManagementFormRawValue['ngScreenerReport']>;
  committeeDecision: FormControl<FraudKnowledgeManagementFormRawValue['committeeDecision']>;
  measureTaken: FormControl<FraudKnowledgeManagementFormRawValue['measureTaken']>;
  fraudAmountRecovered: FormControl<FraudKnowledgeManagementFormRawValue['fraudAmountRecovered']>;
  fraudAmountWrittenOff: FormControl<FraudKnowledgeManagementFormRawValue['fraudAmountWrittenOff']>;
  previouslyHeldForFraudOutstanding: FormControl<FraudKnowledgeManagementFormRawValue['previouslyHeldForFraudOutstanding']>;
  employee: FormControl<FraudKnowledgeManagementFormRawValue['employee']>;
  fraudInvestigationReport: FormControl<FraudKnowledgeManagementFormRawValue['fraudInvestigationReport']>;
  fraudType: FormControl<FraudKnowledgeManagementFormRawValue['fraudType']>;
  bankAccount: FormControl<FraudKnowledgeManagementFormRawValue['bankAccount']>;
  bankService: FormControl<FraudKnowledgeManagementFormRawValue['bankService']>;
  internalEmployee: FormControl<FraudKnowledgeManagementFormRawValue['internalEmployee']>;
  externalEmployee: FormControl<FraudKnowledgeManagementFormRawValue['externalEmployee']>;
};

export type FraudKnowledgeManagementFormGroup = FormGroup<FraudKnowledgeManagementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FraudKnowledgeManagementFormService {
  createFraudKnowledgeManagementFormGroup(
    fraudKnowledgeManagement: FraudKnowledgeManagementFormGroupInput = { id: null }
  ): FraudKnowledgeManagementFormGroup {
    const fraudKnowledgeManagementRawValue = this.convertFraudKnowledgeManagementToFraudKnowledgeManagementRawValue({
      ...this.getFormDefaults(),
      ...fraudKnowledgeManagement,
    });
    return new FormGroup<FraudKnowledgeManagementFormGroupContent>({
      id: new FormControl(
        { value: fraudKnowledgeManagementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      reportNumber: new FormControl(fraudKnowledgeManagementRawValue.reportNumber, {
        validators: [Validators.required],
      }),
      fraudIncident: new FormControl(fraudKnowledgeManagementRawValue.fraudIncident, {
        validators: [Validators.required],
      }),
      actualIncident: new FormControl(fraudKnowledgeManagementRawValue.actualIncident, {
        validators: [Validators.required],
      }),
      attemptIncident: new FormControl(fraudKnowledgeManagementRawValue.attemptIncident, {
        validators: [Validators.required],
      }),
      reasonForFailure: new FormControl(fraudKnowledgeManagementRawValue.reasonForFailure, {
        validators: [Validators.required],
      }),
      unit: new FormControl(fraudKnowledgeManagementRawValue.unit, {
        validators: [Validators.required],
      }),
      incidentDate: new FormControl(fraudKnowledgeManagementRawValue.incidentDate, {
        validators: [Validators.required],
      }),
      dateOfDetection: new FormControl(fraudKnowledgeManagementRawValue.dateOfDetection, {
        validators: [Validators.required],
      }),
      reasonForDelay: new FormControl(fraudKnowledgeManagementRawValue.reasonForDelay),
      projectCreationDate: new FormControl(fraudKnowledgeManagementRawValue.projectCreationDate, {
        validators: [Validators.required],
      }),
      reportDate: new FormControl(fraudKnowledgeManagementRawValue.reportDate, {
        validators: [Validators.required],
      }),
      suspectedFraudster: new FormControl(fraudKnowledgeManagementRawValue.suspectedFraudster, {
        validators: [Validators.required],
      }),
      financialLossAmount: new FormControl(fraudKnowledgeManagementRawValue.financialLossAmount, {
        validators: [Validators.required],
      }),
      actualFraudAmount: new FormControl(fraudKnowledgeManagementRawValue.actualFraudAmount, {
        validators: [Validators.required],
      }),
      debitAccount: new FormControl(fraudKnowledgeManagementRawValue.debitAccount, {
        validators: [Validators.required],
      }),
      creditAccount: new FormControl(fraudKnowledgeManagementRawValue.creditAccount, {
        validators: [Validators.required],
      }),
      techniquesandTechnologiesUsed: new FormControl(fraudKnowledgeManagementRawValue.techniquesandTechnologiesUsed),
      causeForAnIncident: new FormControl(fraudKnowledgeManagementRawValue.causeForAnIncident, {
        validators: [Validators.required],
      }),
      systemAndProceduralLoophole: new FormControl(fraudKnowledgeManagementRawValue.systemAndProceduralLoophole),
      effect: new FormControl(fraudKnowledgeManagementRawValue.effect, {
        validators: [Validators.required],
      }),
      recommendationsDrawn: new FormControl(fraudKnowledgeManagementRawValue.recommendationsDrawn, {
        validators: [Validators.required],
      }),
      positionJG: new FormControl(fraudKnowledgeManagementRawValue.positionJG),
      nameIdNo: new FormControl(fraudKnowledgeManagementRawValue.nameIdNo),
      actionInvolved: new FormControl(fraudKnowledgeManagementRawValue.actionInvolved),
      ngScreenerReport: new FormControl(fraudKnowledgeManagementRawValue.ngScreenerReport),
      committeeDecision: new FormControl(fraudKnowledgeManagementRawValue.committeeDecision),
      measureTaken: new FormControl(fraudKnowledgeManagementRawValue.measureTaken),
      fraudAmountRecovered: new FormControl(fraudKnowledgeManagementRawValue.fraudAmountRecovered),
      fraudAmountWrittenOff: new FormControl(fraudKnowledgeManagementRawValue.fraudAmountWrittenOff),
      previouslyHeldForFraudOutstanding: new FormControl(fraudKnowledgeManagementRawValue.previouslyHeldForFraudOutstanding),
      employee: new FormControl(fraudKnowledgeManagementRawValue.employee),
      fraudInvestigationReport: new FormControl(fraudKnowledgeManagementRawValue.fraudInvestigationReport),
      fraudType: new FormControl(fraudKnowledgeManagementRawValue.fraudType),
      bankAccount: new FormControl(fraudKnowledgeManagementRawValue.bankAccount),
      bankService: new FormControl(fraudKnowledgeManagementRawValue.bankService),
      internalEmployee: new FormControl(fraudKnowledgeManagementRawValue.internalEmployee),
      externalEmployee: new FormControl(fraudKnowledgeManagementRawValue.externalEmployee),
    });
  }

  getFraudKnowledgeManagement(form: FraudKnowledgeManagementFormGroup): IFraudKnowledgeManagement | NewFraudKnowledgeManagement {
    return this.convertFraudKnowledgeManagementRawValueToFraudKnowledgeManagement(
      form.getRawValue() as FraudKnowledgeManagementFormRawValue | NewFraudKnowledgeManagementFormRawValue
    );
  }

  resetForm(form: FraudKnowledgeManagementFormGroup, fraudKnowledgeManagement: FraudKnowledgeManagementFormGroupInput): void {
    const fraudKnowledgeManagementRawValue = this.convertFraudKnowledgeManagementToFraudKnowledgeManagementRawValue({
      ...this.getFormDefaults(),
      ...fraudKnowledgeManagement,
    });
    form.reset(
      {
        ...fraudKnowledgeManagementRawValue,
        id: { value: fraudKnowledgeManagementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FraudKnowledgeManagementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      incidentDate: currentTime,
      dateOfDetection: currentTime,
    };
  }

  private convertFraudKnowledgeManagementRawValueToFraudKnowledgeManagement(
    rawFraudKnowledgeManagement: FraudKnowledgeManagementFormRawValue | NewFraudKnowledgeManagementFormRawValue
  ): IFraudKnowledgeManagement | NewFraudKnowledgeManagement {
    return {
      ...rawFraudKnowledgeManagement,
      incidentDate: dayjs(rawFraudKnowledgeManagement.incidentDate, DATE_TIME_FORMAT),
      dateOfDetection: dayjs(rawFraudKnowledgeManagement.dateOfDetection, DATE_TIME_FORMAT),
    };
  }

  private convertFraudKnowledgeManagementToFraudKnowledgeManagementRawValue(
    fraudKnowledgeManagement: IFraudKnowledgeManagement | (Partial<NewFraudKnowledgeManagement> & FraudKnowledgeManagementFormDefaults)
  ): FraudKnowledgeManagementFormRawValue | PartialWithRequiredKeyOf<NewFraudKnowledgeManagementFormRawValue> {
    return {
      ...fraudKnowledgeManagement,
      incidentDate: fraudKnowledgeManagement.incidentDate ? fraudKnowledgeManagement.incidentDate.format(DATE_TIME_FORMAT) : undefined,
      dateOfDetection: fraudKnowledgeManagement.dateOfDetection
        ? fraudKnowledgeManagement.dateOfDetection.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITeamLead, NewTeamLead } from '../team-lead.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITeamLead for edit and NewTeamLeadFormGroupInput for create.
 */
type TeamLeadFormGroupInput = ITeamLead | PartialWithRequiredKeyOf<NewTeamLead>;

type TeamLeadFormDefaults = Pick<NewTeamLead, 'id'>;

type TeamLeadFormGroupContent = {
  id: FormControl<ITeamLead['id'] | NewTeamLead['id']>;
  teamLeadName: FormControl<ITeamLead['teamLeadName']>;
  description: FormControl<ITeamLead['description']>;
  managerialId: FormControl<ITeamLead['managerialId']>;
  user: FormControl<ITeamLead['user']>;
  managers: FormControl<ITeamLead['managers']>;
};

export type TeamLeadFormGroup = FormGroup<TeamLeadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TeamLeadFormService {
  createTeamLeadFormGroup(teamLead: TeamLeadFormGroupInput = { id: null }): TeamLeadFormGroup {
    const teamLeadRawValue = {
      ...this.getFormDefaults(),
      ...teamLead,
    };
    return new FormGroup<TeamLeadFormGroupContent>({
      id: new FormControl(
        { value: teamLeadRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      teamLeadName: new FormControl(teamLeadRawValue.teamLeadName, {
        validators: [Validators.maxLength(50)],
      }),
      description: new FormControl(teamLeadRawValue.description),
      managerialId: new FormControl(teamLeadRawValue.managerialId),
      user: new FormControl(teamLeadRawValue.user),
      managers: new FormControl(teamLeadRawValue.managers),
    });
  }

  getTeamLead(form: TeamLeadFormGroup): ITeamLead | NewTeamLead {
    return form.getRawValue() as ITeamLead | NewTeamLead;
  }

  resetForm(form: TeamLeadFormGroup, teamLead: TeamLeadFormGroupInput): void {
    const teamLeadRawValue = { ...this.getFormDefaults(), ...teamLead };
    form.reset(
      {
        ...teamLeadRawValue,
        id: { value: teamLeadRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TeamLeadFormDefaults {
    return {
      id: null,
    };
  }
}

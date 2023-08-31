import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TeamFormService, TeamFormGroup } from './team-form.service';
import { ITeam } from '../team.model';
import { TeamService } from '../service/team.service';
import { ITeamLead } from 'app/entities/team-lead/team-lead.model';
import { TeamLeadService } from 'app/entities/team-lead/service/team-lead.service';
import { IManagerial } from 'app/entities/managerial/managerial.model';
import { ManagerialService } from 'app/entities/managerial/service/managerial.service';

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html',
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;
  team: ITeam | null = null;

  teamLeadsSharedCollection: ITeamLead[] = [];
  managerialsSharedCollection: IManagerial[] = [];

  editForm: TeamFormGroup = this.teamFormService.createTeamFormGroup();

  constructor(
    protected teamService: TeamService,
    protected teamFormService: TeamFormService,
    protected teamLeadService: TeamLeadService,
    protected managerialService: ManagerialService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTeamLead = (o1: ITeamLead | null, o2: ITeamLead | null): boolean => this.teamLeadService.compareTeamLead(o1, o2);

  compareManagerial = (o1: IManagerial | null, o2: IManagerial | null): boolean => this.managerialService.compareManagerial(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      this.team = team;
      if (team) {
        this.updateForm(team);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const team = this.teamFormService.getTeam(this.editForm);
    if (team.id !== null) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(team: ITeam): void {
    this.team = team;
    this.teamFormService.resetForm(this.editForm, team);

    this.teamLeadsSharedCollection = this.teamLeadService.addTeamLeadToCollectionIfMissing<ITeamLead>(
      this.teamLeadsSharedCollection,
      team.teamLead
    );
    this.managerialsSharedCollection = this.managerialService.addManagerialToCollectionIfMissing<IManagerial>(
      this.managerialsSharedCollection,
      team.managers
    );
  }

  protected loadRelationshipsOptions(): void {
    this.teamLeadService
      .query()
      .pipe(map((res: HttpResponse<ITeamLead[]>) => res.body ?? []))
      .pipe(
        map((teamLeads: ITeamLead[]) => this.teamLeadService.addTeamLeadToCollectionIfMissing<ITeamLead>(teamLeads, this.team?.teamLead))
      )
      .subscribe((teamLeads: ITeamLead[]) => (this.teamLeadsSharedCollection = teamLeads));

    this.managerialService
      .query()
      .pipe(map((res: HttpResponse<IManagerial[]>) => res.body ?? []))
      .pipe(
        map((managerials: IManagerial[]) =>
          this.managerialService.addManagerialToCollectionIfMissing<IManagerial>(managerials, this.team?.managers)
        )
      )
      .subscribe((managerials: IManagerial[]) => (this.managerialsSharedCollection = managerials));
  }
}

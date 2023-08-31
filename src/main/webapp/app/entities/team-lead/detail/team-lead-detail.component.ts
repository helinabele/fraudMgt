import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamLead } from '../team-lead.model';

@Component({
  selector: 'jhi-team-lead-detail',
  templateUrl: './team-lead-detail.component.html',
})
export class TeamLeadDetailComponent implements OnInit {
  teamLead: ITeamLead | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamLead }) => {
      this.teamLead = teamLead;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

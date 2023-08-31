import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExternalEmployee } from '../external-employee.model';

@Component({
  selector: 'jhi-external-employee-detail',
  templateUrl: './external-employee-detail.component.html',
})
export class ExternalEmployeeDetailComponent implements OnInit {
  externalEmployee: IExternalEmployee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ externalEmployee }) => {
      this.externalEmployee = externalEmployee;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

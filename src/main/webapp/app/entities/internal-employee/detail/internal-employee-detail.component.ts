import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInternalEmployee } from '../internal-employee.model';

@Component({
  selector: 'jhi-internal-employee-detail',
  templateUrl: './internal-employee-detail.component.html',
})
export class InternalEmployeeDetailComponent implements OnInit {
  internalEmployee: IInternalEmployee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ internalEmployee }) => {
      this.internalEmployee = internalEmployee;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

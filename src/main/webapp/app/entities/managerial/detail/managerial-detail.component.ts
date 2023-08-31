import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManagerial } from '../managerial.model';

@Component({
  selector: 'jhi-managerial-detail',
  templateUrl: './managerial-detail.component.html',
})
export class ManagerialDetailComponent implements OnInit {
  managerial: IManagerial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managerial }) => {
      this.managerial = managerial;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

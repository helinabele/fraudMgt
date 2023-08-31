import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankService } from '../bank-service.model';

@Component({
  selector: 'jhi-bank-service-detail',
  templateUrl: './bank-service-detail.component.html',
})
export class BankServiceDetailComponent implements OnInit {
  bankService: IBankService | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankService }) => {
      this.bankService = bankService;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

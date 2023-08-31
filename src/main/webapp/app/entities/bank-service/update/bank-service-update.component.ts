import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BankServiceFormService, BankServiceFormGroup } from './bank-service-form.service';
import { IBankService } from '../bank-service.model';
import { BankServiceService } from '../service/bank-service.service';

@Component({
  selector: 'jhi-bank-service-update',
  templateUrl: './bank-service-update.component.html',
})
export class BankServiceUpdateComponent implements OnInit {
  isSaving = false;
  bankService: IBankService | null = null;

  editForm: BankServiceFormGroup = this.bankServiceFormService.createBankServiceFormGroup();

  constructor(
    protected bankServiceService: BankServiceService,
    protected bankServiceFormService: BankServiceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankService }) => {
      this.bankService = bankService;
      if (bankService) {
        this.updateForm(bankService);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankService = this.bankServiceFormService.getBankService(this.editForm);
    if (bankService.id !== null) {
      this.subscribeToSaveResponse(this.bankServiceService.update(bankService));
    } else {
      this.subscribeToSaveResponse(this.bankServiceService.create(bankService));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankService>>): void {
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

  protected updateForm(bankService: IBankService): void {
    this.bankService = bankService;
    this.bankServiceFormService.resetForm(this.editForm, bankService);
  }
}

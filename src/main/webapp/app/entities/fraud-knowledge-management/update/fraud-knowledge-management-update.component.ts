import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FraudKnowledgeManagementFormService, FraudKnowledgeManagementFormGroup } from './fraud-knowledge-management-form.service';
import { IFraudKnowledgeManagement } from '../fraud-knowledge-management.model';
import { FraudKnowledgeManagementService } from '../service/fraud-knowledge-management.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IFraudInvestigationReport } from 'app/entities/fraud-investigation-report/fraud-investigation-report.model';
import { FraudInvestigationReportService } from 'app/entities/fraud-investigation-report/service/fraud-investigation-report.service';
import { IFraudType } from 'app/entities/fraud-type/fraud-type.model';
import { FraudTypeService } from 'app/entities/fraud-type/service/fraud-type.service';
import { IBankAccount } from 'app/entities/bank-account/bank-account.model';
import { BankAccountService } from 'app/entities/bank-account/service/bank-account.service';
import { IBankService } from 'app/entities/bank-service/bank-service.model';
import { BankServiceService } from 'app/entities/bank-service/service/bank-service.service';
import { IInternalEmployee } from 'app/entities/internal-employee/internal-employee.model';
import { InternalEmployeeService } from 'app/entities/internal-employee/service/internal-employee.service';
import { IExternalEmployee } from 'app/entities/external-employee/external-employee.model';
import { ExternalEmployeeService } from 'app/entities/external-employee/service/external-employee.service';
import { FraudTypeByIncident } from 'app/entities/enumerations/fraud-type-by-incident.model';
import { SuspectedFraudster } from 'app/entities/enumerations/suspected-fraudster.model';

@Component({
  selector: 'jhi-fraud-knowledge-management-update',
  templateUrl: './fraud-knowledge-management-update.component.html',
})
export class FraudKnowledgeManagementUpdateComponent implements OnInit {
  isSaving = false;
  fraudKnowledgeManagement: IFraudKnowledgeManagement | null = null;
  fraudTypeByIncidentValues = Object.keys(FraudTypeByIncident);
  suspectedFraudsterValues = Object.keys(SuspectedFraudster);

  employeesSharedCollection: IEmployee[] = [];
  fraudInvestigationReportsSharedCollection: IFraudInvestigationReport[] = [];
  fraudTypesSharedCollection: IFraudType[] = [];
  bankAccountsSharedCollection: IBankAccount[] = [];
  bankServicesSharedCollection: IBankService[] = [];
  internalEmployeesSharedCollection: IInternalEmployee[] = [];
  externalEmployeesSharedCollection: IExternalEmployee[] = [];

  editForm: FraudKnowledgeManagementFormGroup = this.fraudKnowledgeManagementFormService.createFraudKnowledgeManagementFormGroup();

  constructor(
    protected fraudKnowledgeManagementService: FraudKnowledgeManagementService,
    protected fraudKnowledgeManagementFormService: FraudKnowledgeManagementFormService,
    protected employeeService: EmployeeService,
    protected fraudInvestigationReportService: FraudInvestigationReportService,
    protected fraudTypeService: FraudTypeService,
    protected bankAccountService: BankAccountService,
    protected bankServiceService: BankServiceService,
    protected internalEmployeeService: InternalEmployeeService,
    protected externalEmployeeService: ExternalEmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareFraudInvestigationReport = (o1: IFraudInvestigationReport | null, o2: IFraudInvestigationReport | null): boolean =>
    this.fraudInvestigationReportService.compareFraudInvestigationReport(o1, o2);

  compareFraudType = (o1: IFraudType | null, o2: IFraudType | null): boolean => this.fraudTypeService.compareFraudType(o1, o2);

  compareBankAccount = (o1: IBankAccount | null, o2: IBankAccount | null): boolean => this.bankAccountService.compareBankAccount(o1, o2);

  compareBankService = (o1: IBankService | null, o2: IBankService | null): boolean => this.bankServiceService.compareBankService(o1, o2);

  compareInternalEmployee = (o1: IInternalEmployee | null, o2: IInternalEmployee | null): boolean =>
    this.internalEmployeeService.compareInternalEmployee(o1, o2);

  compareExternalEmployee = (o1: IExternalEmployee | null, o2: IExternalEmployee | null): boolean =>
    this.externalEmployeeService.compareExternalEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fraudKnowledgeManagement }) => {
      this.fraudKnowledgeManagement = fraudKnowledgeManagement;
      if (fraudKnowledgeManagement) {
        this.updateForm(fraudKnowledgeManagement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fraudKnowledgeManagement = this.fraudKnowledgeManagementFormService.getFraudKnowledgeManagement(this.editForm);
    if (fraudKnowledgeManagement.id !== null) {
      this.subscribeToSaveResponse(this.fraudKnowledgeManagementService.update(fraudKnowledgeManagement));
    } else {
      this.subscribeToSaveResponse(this.fraudKnowledgeManagementService.create(fraudKnowledgeManagement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFraudKnowledgeManagement>>): void {
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

  protected updateForm(fraudKnowledgeManagement: IFraudKnowledgeManagement): void {
    this.fraudKnowledgeManagement = fraudKnowledgeManagement;
    this.fraudKnowledgeManagementFormService.resetForm(this.editForm, fraudKnowledgeManagement);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      fraudKnowledgeManagement.employee
    );
    this.fraudInvestigationReportsSharedCollection =
      this.fraudInvestigationReportService.addFraudInvestigationReportToCollectionIfMissing<IFraudInvestigationReport>(
        this.fraudInvestigationReportsSharedCollection,
        fraudKnowledgeManagement.fraudInvestigationReport
      );
    this.fraudTypesSharedCollection = this.fraudTypeService.addFraudTypeToCollectionIfMissing<IFraudType>(
      this.fraudTypesSharedCollection,
      fraudKnowledgeManagement.fraudType
    );
    this.bankAccountsSharedCollection = this.bankAccountService.addBankAccountToCollectionIfMissing<IBankAccount>(
      this.bankAccountsSharedCollection,
      fraudKnowledgeManagement.bankAccount
    );
    this.bankServicesSharedCollection = this.bankServiceService.addBankServiceToCollectionIfMissing<IBankService>(
      this.bankServicesSharedCollection,
      fraudKnowledgeManagement.bankService
    );
    this.internalEmployeesSharedCollection = this.internalEmployeeService.addInternalEmployeeToCollectionIfMissing<IInternalEmployee>(
      this.internalEmployeesSharedCollection,
      fraudKnowledgeManagement.internalEmployee
    );
    this.externalEmployeesSharedCollection = this.externalEmployeeService.addExternalEmployeeToCollectionIfMissing<IExternalEmployee>(
      this.externalEmployeesSharedCollection,
      fraudKnowledgeManagement.externalEmployee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.fraudKnowledgeManagement?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.fraudInvestigationReportService
      .query()
      .pipe(map((res: HttpResponse<IFraudInvestigationReport[]>) => res.body ?? []))
      .pipe(
        map((fraudInvestigationReports: IFraudInvestigationReport[]) =>
          this.fraudInvestigationReportService.addFraudInvestigationReportToCollectionIfMissing<IFraudInvestigationReport>(
            fraudInvestigationReports,
            this.fraudKnowledgeManagement?.fraudInvestigationReport
          )
        )
      )
      .subscribe(
        (fraudInvestigationReports: IFraudInvestigationReport[]) =>
          (this.fraudInvestigationReportsSharedCollection = fraudInvestigationReports)
      );

    this.fraudTypeService
      .query()
      .pipe(map((res: HttpResponse<IFraudType[]>) => res.body ?? []))
      .pipe(
        map((fraudTypes: IFraudType[]) =>
          this.fraudTypeService.addFraudTypeToCollectionIfMissing<IFraudType>(fraudTypes, this.fraudKnowledgeManagement?.fraudType)
        )
      )
      .subscribe((fraudTypes: IFraudType[]) => (this.fraudTypesSharedCollection = fraudTypes));

    this.bankAccountService
      .query()
      .pipe(map((res: HttpResponse<IBankAccount[]>) => res.body ?? []))
      .pipe(
        map((bankAccounts: IBankAccount[]) =>
          this.bankAccountService.addBankAccountToCollectionIfMissing<IBankAccount>(
            bankAccounts,
            this.fraudKnowledgeManagement?.bankAccount
          )
        )
      )
      .subscribe((bankAccounts: IBankAccount[]) => (this.bankAccountsSharedCollection = bankAccounts));

    this.bankServiceService
      .query()
      .pipe(map((res: HttpResponse<IBankService[]>) => res.body ?? []))
      .pipe(
        map((bankServices: IBankService[]) =>
          this.bankServiceService.addBankServiceToCollectionIfMissing<IBankService>(
            bankServices,
            this.fraudKnowledgeManagement?.bankService
          )
        )
      )
      .subscribe((bankServices: IBankService[]) => (this.bankServicesSharedCollection = bankServices));

    this.internalEmployeeService
      .query()
      .pipe(map((res: HttpResponse<IInternalEmployee[]>) => res.body ?? []))
      .pipe(
        map((internalEmployees: IInternalEmployee[]) =>
          this.internalEmployeeService.addInternalEmployeeToCollectionIfMissing<IInternalEmployee>(
            internalEmployees,
            this.fraudKnowledgeManagement?.internalEmployee
          )
        )
      )
      .subscribe((internalEmployees: IInternalEmployee[]) => (this.internalEmployeesSharedCollection = internalEmployees));

    this.externalEmployeeService
      .query()
      .pipe(map((res: HttpResponse<IExternalEmployee[]>) => res.body ?? []))
      .pipe(
        map((externalEmployees: IExternalEmployee[]) =>
          this.externalEmployeeService.addExternalEmployeeToCollectionIfMissing<IExternalEmployee>(
            externalEmployees,
            this.fraudKnowledgeManagement?.externalEmployee
          )
        )
      )
      .subscribe((externalEmployees: IExternalEmployee[]) => (this.externalEmployeesSharedCollection = externalEmployees));
  }
}

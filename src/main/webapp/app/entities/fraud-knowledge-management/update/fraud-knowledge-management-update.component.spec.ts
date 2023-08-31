import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FraudKnowledgeManagementFormService } from './fraud-knowledge-management-form.service';
import { FraudKnowledgeManagementService } from '../service/fraud-knowledge-management.service';
import { IFraudKnowledgeManagement } from '../fraud-knowledge-management.model';
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

import { FraudKnowledgeManagementUpdateComponent } from './fraud-knowledge-management-update.component';

describe('FraudKnowledgeManagement Management Update Component', () => {
  let comp: FraudKnowledgeManagementUpdateComponent;
  let fixture: ComponentFixture<FraudKnowledgeManagementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fraudKnowledgeManagementFormService: FraudKnowledgeManagementFormService;
  let fraudKnowledgeManagementService: FraudKnowledgeManagementService;
  let employeeService: EmployeeService;
  let fraudInvestigationReportService: FraudInvestigationReportService;
  let fraudTypeService: FraudTypeService;
  let bankAccountService: BankAccountService;
  let bankServiceService: BankServiceService;
  let internalEmployeeService: InternalEmployeeService;
  let externalEmployeeService: ExternalEmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FraudKnowledgeManagementUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FraudKnowledgeManagementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FraudKnowledgeManagementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fraudKnowledgeManagementFormService = TestBed.inject(FraudKnowledgeManagementFormService);
    fraudKnowledgeManagementService = TestBed.inject(FraudKnowledgeManagementService);
    employeeService = TestBed.inject(EmployeeService);
    fraudInvestigationReportService = TestBed.inject(FraudInvestigationReportService);
    fraudTypeService = TestBed.inject(FraudTypeService);
    bankAccountService = TestBed.inject(BankAccountService);
    bankServiceService = TestBed.inject(BankServiceService);
    internalEmployeeService = TestBed.inject(InternalEmployeeService);
    externalEmployeeService = TestBed.inject(ExternalEmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const employee: IEmployee = { id: '7a034029-e6cb-4c79-86e6-aab603080f19' };
      fraudKnowledgeManagement.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: '8c0aa416-0f0c-4281-a7fa-b72fbaa1a8d2' }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FraudInvestigationReport query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const fraudInvestigationReport: IFraudInvestigationReport = { id: '0f62affa-12c9-4a26-af04-6097f568588b' };
      fraudKnowledgeManagement.fraudInvestigationReport = fraudInvestigationReport;

      const fraudInvestigationReportCollection: IFraudInvestigationReport[] = [{ id: '9e37c5db-b08d-4a6c-892c-99bf859cd60f' }];
      jest
        .spyOn(fraudInvestigationReportService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: fraudInvestigationReportCollection })));
      const additionalFraudInvestigationReports = [fraudInvestigationReport];
      const expectedCollection: IFraudInvestigationReport[] = [
        ...additionalFraudInvestigationReports,
        ...fraudInvestigationReportCollection,
      ];
      jest.spyOn(fraudInvestigationReportService, 'addFraudInvestigationReportToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(fraudInvestigationReportService.query).toHaveBeenCalled();
      expect(fraudInvestigationReportService.addFraudInvestigationReportToCollectionIfMissing).toHaveBeenCalledWith(
        fraudInvestigationReportCollection,
        ...additionalFraudInvestigationReports.map(expect.objectContaining)
      );
      expect(comp.fraudInvestigationReportsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FraudType query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const fraudType: IFraudType = { id: '13fedf2c-2ed8-4426-87ad-b1fe84b41eac' };
      fraudKnowledgeManagement.fraudType = fraudType;

      const fraudTypeCollection: IFraudType[] = [{ id: '06df0abc-82ef-4318-9a42-fb40172097a6' }];
      jest.spyOn(fraudTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: fraudTypeCollection })));
      const additionalFraudTypes = [fraudType];
      const expectedCollection: IFraudType[] = [...additionalFraudTypes, ...fraudTypeCollection];
      jest.spyOn(fraudTypeService, 'addFraudTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(fraudTypeService.query).toHaveBeenCalled();
      expect(fraudTypeService.addFraudTypeToCollectionIfMissing).toHaveBeenCalledWith(
        fraudTypeCollection,
        ...additionalFraudTypes.map(expect.objectContaining)
      );
      expect(comp.fraudTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BankAccount query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const bankAccount: IBankAccount = { id: '9042011b-e6d3-468c-b292-5fe94cbc157f' };
      fraudKnowledgeManagement.bankAccount = bankAccount;

      const bankAccountCollection: IBankAccount[] = [{ id: 'd905b71a-f54c-4a5c-bf5a-ba83a32b61c0' }];
      jest.spyOn(bankAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: bankAccountCollection })));
      const additionalBankAccounts = [bankAccount];
      const expectedCollection: IBankAccount[] = [...additionalBankAccounts, ...bankAccountCollection];
      jest.spyOn(bankAccountService, 'addBankAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(bankAccountService.query).toHaveBeenCalled();
      expect(bankAccountService.addBankAccountToCollectionIfMissing).toHaveBeenCalledWith(
        bankAccountCollection,
        ...additionalBankAccounts.map(expect.objectContaining)
      );
      expect(comp.bankAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BankService query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const bankService: IBankService = { id: '06e02f72-673e-47b5-82c7-5c03b80a544b' };
      fraudKnowledgeManagement.bankService = bankService;

      const bankServiceCollection: IBankService[] = [{ id: 'f76be2d3-1076-456b-b2f9-229eea911b5e' }];
      jest.spyOn(bankServiceService, 'query').mockReturnValue(of(new HttpResponse({ body: bankServiceCollection })));
      const additionalBankServices = [bankService];
      const expectedCollection: IBankService[] = [...additionalBankServices, ...bankServiceCollection];
      jest.spyOn(bankServiceService, 'addBankServiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(bankServiceService.query).toHaveBeenCalled();
      expect(bankServiceService.addBankServiceToCollectionIfMissing).toHaveBeenCalledWith(
        bankServiceCollection,
        ...additionalBankServices.map(expect.objectContaining)
      );
      expect(comp.bankServicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call InternalEmployee query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const internalEmployee: IInternalEmployee = { id: '835b20c6-11f5-434c-b4ed-ab266635d58a' };
      fraudKnowledgeManagement.internalEmployee = internalEmployee;

      const internalEmployeeCollection: IInternalEmployee[] = [{ id: '71b49a72-3b2d-4f5a-830e-76749984e2fa' }];
      jest.spyOn(internalEmployeeService, 'query').mockReturnValue(of(new HttpResponse({ body: internalEmployeeCollection })));
      const additionalInternalEmployees = [internalEmployee];
      const expectedCollection: IInternalEmployee[] = [...additionalInternalEmployees, ...internalEmployeeCollection];
      jest.spyOn(internalEmployeeService, 'addInternalEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(internalEmployeeService.query).toHaveBeenCalled();
      expect(internalEmployeeService.addInternalEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        internalEmployeeCollection,
        ...additionalInternalEmployees.map(expect.objectContaining)
      );
      expect(comp.internalEmployeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ExternalEmployee query and add missing value', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const externalEmployee: IExternalEmployee = { id: '3670dc9e-b3ee-42f2-828a-3a4aa2c7f834' };
      fraudKnowledgeManagement.externalEmployee = externalEmployee;

      const externalEmployeeCollection: IExternalEmployee[] = [{ id: 'ac7925c7-018c-44b4-8f39-4e90f0ac1916' }];
      jest.spyOn(externalEmployeeService, 'query').mockReturnValue(of(new HttpResponse({ body: externalEmployeeCollection })));
      const additionalExternalEmployees = [externalEmployee];
      const expectedCollection: IExternalEmployee[] = [...additionalExternalEmployees, ...externalEmployeeCollection];
      jest.spyOn(externalEmployeeService, 'addExternalEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(externalEmployeeService.query).toHaveBeenCalled();
      expect(externalEmployeeService.addExternalEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        externalEmployeeCollection,
        ...additionalExternalEmployees.map(expect.objectContaining)
      );
      expect(comp.externalEmployeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fraudKnowledgeManagement: IFraudKnowledgeManagement = { id: 'CBA' };
      const employee: IEmployee = { id: 'bf9bcf32-6bc9-4102-becc-3b66c58d551d' };
      fraudKnowledgeManagement.employee = employee;
      const fraudInvestigationReport: IFraudInvestigationReport = { id: '77163985-86e8-4008-addb-2387a041c6f5' };
      fraudKnowledgeManagement.fraudInvestigationReport = fraudInvestigationReport;
      const fraudType: IFraudType = { id: '79720c8d-a2f8-462d-9c41-0c57834d174d' };
      fraudKnowledgeManagement.fraudType = fraudType;
      const bankAccount: IBankAccount = { id: 'f4b5bae7-7466-4081-9bc8-7af69411b673' };
      fraudKnowledgeManagement.bankAccount = bankAccount;
      const bankService: IBankService = { id: 'aec5c638-62e4-460e-a605-6e9876423aec' };
      fraudKnowledgeManagement.bankService = bankService;
      const internalEmployee: IInternalEmployee = { id: 'cf3290f1-4202-4923-afd2-fc5ad9c66120' };
      fraudKnowledgeManagement.internalEmployee = internalEmployee;
      const externalEmployee: IExternalEmployee = { id: 'c624539c-83ad-424a-b217-a44a4750a721' };
      fraudKnowledgeManagement.externalEmployee = externalEmployee;

      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.fraudInvestigationReportsSharedCollection).toContain(fraudInvestigationReport);
      expect(comp.fraudTypesSharedCollection).toContain(fraudType);
      expect(comp.bankAccountsSharedCollection).toContain(bankAccount);
      expect(comp.bankServicesSharedCollection).toContain(bankService);
      expect(comp.internalEmployeesSharedCollection).toContain(internalEmployee);
      expect(comp.externalEmployeesSharedCollection).toContain(externalEmployee);
      expect(comp.fraudKnowledgeManagement).toEqual(fraudKnowledgeManagement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFraudKnowledgeManagement>>();
      const fraudKnowledgeManagement = { id: 'ABC' };
      jest.spyOn(fraudKnowledgeManagementFormService, 'getFraudKnowledgeManagement').mockReturnValue(fraudKnowledgeManagement);
      jest.spyOn(fraudKnowledgeManagementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fraudKnowledgeManagement }));
      saveSubject.complete();

      // THEN
      expect(fraudKnowledgeManagementFormService.getFraudKnowledgeManagement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fraudKnowledgeManagementService.update).toHaveBeenCalledWith(expect.objectContaining(fraudKnowledgeManagement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFraudKnowledgeManagement>>();
      const fraudKnowledgeManagement = { id: 'ABC' };
      jest.spyOn(fraudKnowledgeManagementFormService, 'getFraudKnowledgeManagement').mockReturnValue({ id: null });
      jest.spyOn(fraudKnowledgeManagementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fraudKnowledgeManagement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fraudKnowledgeManagement }));
      saveSubject.complete();

      // THEN
      expect(fraudKnowledgeManagementFormService.getFraudKnowledgeManagement).toHaveBeenCalled();
      expect(fraudKnowledgeManagementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFraudKnowledgeManagement>>();
      const fraudKnowledgeManagement = { id: 'ABC' };
      jest.spyOn(fraudKnowledgeManagementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fraudKnowledgeManagement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fraudKnowledgeManagementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFraudInvestigationReport', () => {
      it('Should forward to fraudInvestigationReportService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(fraudInvestigationReportService, 'compareFraudInvestigationReport');
        comp.compareFraudInvestigationReport(entity, entity2);
        expect(fraudInvestigationReportService.compareFraudInvestigationReport).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFraudType', () => {
      it('Should forward to fraudTypeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(fraudTypeService, 'compareFraudType');
        comp.compareFraudType(entity, entity2);
        expect(fraudTypeService.compareFraudType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBankAccount', () => {
      it('Should forward to bankAccountService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(bankAccountService, 'compareBankAccount');
        comp.compareBankAccount(entity, entity2);
        expect(bankAccountService.compareBankAccount).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBankService', () => {
      it('Should forward to bankServiceService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(bankServiceService, 'compareBankService');
        comp.compareBankService(entity, entity2);
        expect(bankServiceService.compareBankService).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareInternalEmployee', () => {
      it('Should forward to internalEmployeeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(internalEmployeeService, 'compareInternalEmployee');
        comp.compareInternalEmployee(entity, entity2);
        expect(internalEmployeeService.compareInternalEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareExternalEmployee', () => {
      it('Should forward to externalEmployeeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(externalEmployeeService, 'compareExternalEmployee');
        comp.compareExternalEmployee(entity, entity2);
        expect(externalEmployeeService.compareExternalEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

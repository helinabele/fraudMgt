import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FindingsFormService } from './findings-form.service';
import { FindingsService } from '../service/findings.service';
import { IFindings } from '../findings.model';
import { IFraudInvestigationReport } from 'app/entities/fraud-investigation-report/fraud-investigation-report.model';
import { FraudInvestigationReportService } from 'app/entities/fraud-investigation-report/service/fraud-investigation-report.service';

import { FindingsUpdateComponent } from './findings-update.component';

describe('Findings Management Update Component', () => {
  let comp: FindingsUpdateComponent;
  let fixture: ComponentFixture<FindingsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let findingsFormService: FindingsFormService;
  let findingsService: FindingsService;
  let fraudInvestigationReportService: FraudInvestigationReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FindingsUpdateComponent],
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
      .overrideTemplate(FindingsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FindingsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    findingsFormService = TestBed.inject(FindingsFormService);
    findingsService = TestBed.inject(FindingsService);
    fraudInvestigationReportService = TestBed.inject(FraudInvestigationReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FraudInvestigationReport query and add missing value', () => {
      const findings: IFindings = { id: 'CBA' };
      const fraudInvestigationReport: IFraudInvestigationReport = { id: '469a2fa1-af16-40cc-a4d4-cfaf34ec9e19' };
      findings.fraudInvestigationReport = fraudInvestigationReport;

      const fraudInvestigationReportCollection: IFraudInvestigationReport[] = [{ id: 'b115ad31-55d2-41e1-9386-8e8d41430460' }];
      jest
        .spyOn(fraudInvestigationReportService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: fraudInvestigationReportCollection })));
      const additionalFraudInvestigationReports = [fraudInvestigationReport];
      const expectedCollection: IFraudInvestigationReport[] = [
        ...additionalFraudInvestigationReports,
        ...fraudInvestigationReportCollection,
      ];
      jest.spyOn(fraudInvestigationReportService, 'addFraudInvestigationReportToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ findings });
      comp.ngOnInit();

      expect(fraudInvestigationReportService.query).toHaveBeenCalled();
      expect(fraudInvestigationReportService.addFraudInvestigationReportToCollectionIfMissing).toHaveBeenCalledWith(
        fraudInvestigationReportCollection,
        ...additionalFraudInvestigationReports.map(expect.objectContaining)
      );
      expect(comp.fraudInvestigationReportsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const findings: IFindings = { id: 'CBA' };
      const fraudInvestigationReport: IFraudInvestigationReport = { id: '88dbcbf2-729b-4dfb-9ec3-41009fde8427' };
      findings.fraudInvestigationReport = fraudInvestigationReport;

      activatedRoute.data = of({ findings });
      comp.ngOnInit();

      expect(comp.fraudInvestigationReportsSharedCollection).toContain(fraudInvestigationReport);
      expect(comp.findings).toEqual(findings);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFindings>>();
      const findings = { id: 'ABC' };
      jest.spyOn(findingsFormService, 'getFindings').mockReturnValue(findings);
      jest.spyOn(findingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ findings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: findings }));
      saveSubject.complete();

      // THEN
      expect(findingsFormService.getFindings).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(findingsService.update).toHaveBeenCalledWith(expect.objectContaining(findings));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFindings>>();
      const findings = { id: 'ABC' };
      jest.spyOn(findingsFormService, 'getFindings').mockReturnValue({ id: null });
      jest.spyOn(findingsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ findings: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: findings }));
      saveSubject.complete();

      // THEN
      expect(findingsFormService.getFindings).toHaveBeenCalled();
      expect(findingsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFindings>>();
      const findings = { id: 'ABC' };
      jest.spyOn(findingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ findings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(findingsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFraudInvestigationReport', () => {
      it('Should forward to fraudInvestigationReportService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(fraudInvestigationReportService, 'compareFraudInvestigationReport');
        comp.compareFraudInvestigationReport(entity, entity2);
        expect(fraudInvestigationReportService.compareFraudInvestigationReport).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

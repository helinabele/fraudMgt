import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WhistleBlowerReportFormService } from './whistle-blower-report-form.service';
import { WhistleBlowerReportService } from '../service/whistle-blower-report.service';
import { IWhistleBlowerReport } from '../whistle-blower-report.model';

import { WhistleBlowerReportUpdateComponent } from './whistle-blower-report-update.component';

describe('WhistleBlowerReport Management Update Component', () => {
  let comp: WhistleBlowerReportUpdateComponent;
  let fixture: ComponentFixture<WhistleBlowerReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let whistleBlowerReportFormService: WhistleBlowerReportFormService;
  let whistleBlowerReportService: WhistleBlowerReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WhistleBlowerReportUpdateComponent],
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
      .overrideTemplate(WhistleBlowerReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WhistleBlowerReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    whistleBlowerReportFormService = TestBed.inject(WhistleBlowerReportFormService);
    whistleBlowerReportService = TestBed.inject(WhistleBlowerReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const whistleBlowerReport: IWhistleBlowerReport = { id: 'CBA' };

      activatedRoute.data = of({ whistleBlowerReport });
      comp.ngOnInit();

      expect(comp.whistleBlowerReport).toEqual(whistleBlowerReport);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWhistleBlowerReport>>();
      const whistleBlowerReport = { id: 'ABC' };
      jest.spyOn(whistleBlowerReportFormService, 'getWhistleBlowerReport').mockReturnValue(whistleBlowerReport);
      jest.spyOn(whistleBlowerReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ whistleBlowerReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: whistleBlowerReport }));
      saveSubject.complete();

      // THEN
      expect(whistleBlowerReportFormService.getWhistleBlowerReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(whistleBlowerReportService.update).toHaveBeenCalledWith(expect.objectContaining(whistleBlowerReport));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWhistleBlowerReport>>();
      const whistleBlowerReport = { id: 'ABC' };
      jest.spyOn(whistleBlowerReportFormService, 'getWhistleBlowerReport').mockReturnValue({ id: null });
      jest.spyOn(whistleBlowerReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ whistleBlowerReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: whistleBlowerReport }));
      saveSubject.complete();

      // THEN
      expect(whistleBlowerReportFormService.getWhistleBlowerReport).toHaveBeenCalled();
      expect(whistleBlowerReportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWhistleBlowerReport>>();
      const whistleBlowerReport = { id: 'ABC' };
      jest.spyOn(whistleBlowerReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ whistleBlowerReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(whistleBlowerReportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

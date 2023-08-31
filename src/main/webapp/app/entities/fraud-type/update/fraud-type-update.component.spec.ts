import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FraudTypeFormService } from './fraud-type-form.service';
import { FraudTypeService } from '../service/fraud-type.service';
import { IFraudType } from '../fraud-type.model';

import { FraudTypeUpdateComponent } from './fraud-type-update.component';

describe('FraudType Management Update Component', () => {
  let comp: FraudTypeUpdateComponent;
  let fixture: ComponentFixture<FraudTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fraudTypeFormService: FraudTypeFormService;
  let fraudTypeService: FraudTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FraudTypeUpdateComponent],
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
      .overrideTemplate(FraudTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FraudTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fraudTypeFormService = TestBed.inject(FraudTypeFormService);
    fraudTypeService = TestBed.inject(FraudTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fraudType: IFraudType = { id: 'CBA' };

      activatedRoute.data = of({ fraudType });
      comp.ngOnInit();

      expect(comp.fraudType).toEqual(fraudType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFraudType>>();
      const fraudType = { id: 'ABC' };
      jest.spyOn(fraudTypeFormService, 'getFraudType').mockReturnValue(fraudType);
      jest.spyOn(fraudTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fraudType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fraudType }));
      saveSubject.complete();

      // THEN
      expect(fraudTypeFormService.getFraudType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fraudTypeService.update).toHaveBeenCalledWith(expect.objectContaining(fraudType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFraudType>>();
      const fraudType = { id: 'ABC' };
      jest.spyOn(fraudTypeFormService, 'getFraudType').mockReturnValue({ id: null });
      jest.spyOn(fraudTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fraudType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fraudType }));
      saveSubject.complete();

      // THEN
      expect(fraudTypeFormService.getFraudType).toHaveBeenCalled();
      expect(fraudTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFraudType>>();
      const fraudType = { id: 'ABC' };
      jest.spyOn(fraudTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fraudType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fraudTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BankServiceFormService } from './bank-service-form.service';
import { BankServiceService } from '../service/bank-service.service';
import { IBankService } from '../bank-service.model';

import { BankServiceUpdateComponent } from './bank-service-update.component';

describe('BankService Management Update Component', () => {
  let comp: BankServiceUpdateComponent;
  let fixture: ComponentFixture<BankServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankServiceFormService: BankServiceFormService;
  let bankServiceService: BankServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BankServiceUpdateComponent],
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
      .overrideTemplate(BankServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BankServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bankServiceFormService = TestBed.inject(BankServiceFormService);
    bankServiceService = TestBed.inject(BankServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bankService: IBankService = { id: 'CBA' };

      activatedRoute.data = of({ bankService });
      comp.ngOnInit();

      expect(comp.bankService).toEqual(bankService);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankService>>();
      const bankService = { id: 'ABC' };
      jest.spyOn(bankServiceFormService, 'getBankService').mockReturnValue(bankService);
      jest.spyOn(bankServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankService }));
      saveSubject.complete();

      // THEN
      expect(bankServiceFormService.getBankService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bankServiceService.update).toHaveBeenCalledWith(expect.objectContaining(bankService));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankService>>();
      const bankService = { id: 'ABC' };
      jest.spyOn(bankServiceFormService, 'getBankService').mockReturnValue({ id: null });
      jest.spyOn(bankServiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankService: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankService }));
      saveSubject.complete();

      // THEN
      expect(bankServiceFormService.getBankService).toHaveBeenCalled();
      expect(bankServiceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankService>>();
      const bankService = { id: 'ABC' };
      jest.spyOn(bankServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bankServiceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

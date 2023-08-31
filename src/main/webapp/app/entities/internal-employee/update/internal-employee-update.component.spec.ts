import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InternalEmployeeFormService } from './internal-employee-form.service';
import { InternalEmployeeService } from '../service/internal-employee.service';
import { IInternalEmployee } from '../internal-employee.model';

import { InternalEmployeeUpdateComponent } from './internal-employee-update.component';

describe('InternalEmployee Management Update Component', () => {
  let comp: InternalEmployeeUpdateComponent;
  let fixture: ComponentFixture<InternalEmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let internalEmployeeFormService: InternalEmployeeFormService;
  let internalEmployeeService: InternalEmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InternalEmployeeUpdateComponent],
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
      .overrideTemplate(InternalEmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InternalEmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    internalEmployeeFormService = TestBed.inject(InternalEmployeeFormService);
    internalEmployeeService = TestBed.inject(InternalEmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const internalEmployee: IInternalEmployee = { id: 'CBA' };

      activatedRoute.data = of({ internalEmployee });
      comp.ngOnInit();

      expect(comp.internalEmployee).toEqual(internalEmployee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInternalEmployee>>();
      const internalEmployee = { id: 'ABC' };
      jest.spyOn(internalEmployeeFormService, 'getInternalEmployee').mockReturnValue(internalEmployee);
      jest.spyOn(internalEmployeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ internalEmployee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: internalEmployee }));
      saveSubject.complete();

      // THEN
      expect(internalEmployeeFormService.getInternalEmployee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(internalEmployeeService.update).toHaveBeenCalledWith(expect.objectContaining(internalEmployee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInternalEmployee>>();
      const internalEmployee = { id: 'ABC' };
      jest.spyOn(internalEmployeeFormService, 'getInternalEmployee').mockReturnValue({ id: null });
      jest.spyOn(internalEmployeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ internalEmployee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: internalEmployee }));
      saveSubject.complete();

      // THEN
      expect(internalEmployeeFormService.getInternalEmployee).toHaveBeenCalled();
      expect(internalEmployeeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInternalEmployee>>();
      const internalEmployee = { id: 'ABC' };
      jest.spyOn(internalEmployeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ internalEmployee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(internalEmployeeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

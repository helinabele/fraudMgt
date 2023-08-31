import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExternalEmployeeFormService } from './external-employee-form.service';
import { ExternalEmployeeService } from '../service/external-employee.service';
import { IExternalEmployee } from '../external-employee.model';

import { ExternalEmployeeUpdateComponent } from './external-employee-update.component';

describe('ExternalEmployee Management Update Component', () => {
  let comp: ExternalEmployeeUpdateComponent;
  let fixture: ComponentFixture<ExternalEmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let externalEmployeeFormService: ExternalEmployeeFormService;
  let externalEmployeeService: ExternalEmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExternalEmployeeUpdateComponent],
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
      .overrideTemplate(ExternalEmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExternalEmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    externalEmployeeFormService = TestBed.inject(ExternalEmployeeFormService);
    externalEmployeeService = TestBed.inject(ExternalEmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const externalEmployee: IExternalEmployee = { id: 'CBA' };

      activatedRoute.data = of({ externalEmployee });
      comp.ngOnInit();

      expect(comp.externalEmployee).toEqual(externalEmployee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExternalEmployee>>();
      const externalEmployee = { id: 'ABC' };
      jest.spyOn(externalEmployeeFormService, 'getExternalEmployee').mockReturnValue(externalEmployee);
      jest.spyOn(externalEmployeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ externalEmployee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: externalEmployee }));
      saveSubject.complete();

      // THEN
      expect(externalEmployeeFormService.getExternalEmployee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(externalEmployeeService.update).toHaveBeenCalledWith(expect.objectContaining(externalEmployee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExternalEmployee>>();
      const externalEmployee = { id: 'ABC' };
      jest.spyOn(externalEmployeeFormService, 'getExternalEmployee').mockReturnValue({ id: null });
      jest.spyOn(externalEmployeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ externalEmployee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: externalEmployee }));
      saveSubject.complete();

      // THEN
      expect(externalEmployeeFormService.getExternalEmployee).toHaveBeenCalled();
      expect(externalEmployeeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExternalEmployee>>();
      const externalEmployee = { id: 'ABC' };
      jest.spyOn(externalEmployeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ externalEmployee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(externalEmployeeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

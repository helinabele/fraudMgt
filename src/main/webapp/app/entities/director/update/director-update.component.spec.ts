import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DirectorFormService } from './director-form.service';
import { DirectorService } from '../service/director.service';
import { IDirector } from '../director.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { DirectorUpdateComponent } from './director-update.component';

describe('Director Management Update Component', () => {
  let comp: DirectorUpdateComponent;
  let fixture: ComponentFixture<DirectorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let directorFormService: DirectorFormService;
  let directorService: DirectorService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DirectorUpdateComponent],
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
      .overrideTemplate(DirectorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DirectorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    directorFormService = TestBed.inject(DirectorFormService);
    directorService = TestBed.inject(DirectorService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const director: IDirector = { id: 'CBA' };
      const user: IUser = { id: '687ceabe-9af7-4eb8-87eb-ebfc153214c4' };
      director.user = user;

      const userCollection: IUser[] = [{ id: 'db768a33-f69c-4d70-8898-bef0b814bf4a' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ director });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const director: IDirector = { id: 'CBA' };
      const user: IUser = { id: 'e24ec9d5-1b6e-4377-880a-1e5af584d379' };
      director.user = user;

      activatedRoute.data = of({ director });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.director).toEqual(director);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirector>>();
      const director = { id: 'ABC' };
      jest.spyOn(directorFormService, 'getDirector').mockReturnValue(director);
      jest.spyOn(directorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ director });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: director }));
      saveSubject.complete();

      // THEN
      expect(directorFormService.getDirector).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(directorService.update).toHaveBeenCalledWith(expect.objectContaining(director));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirector>>();
      const director = { id: 'ABC' };
      jest.spyOn(directorFormService, 'getDirector').mockReturnValue({ id: null });
      jest.spyOn(directorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ director: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: director }));
      saveSubject.complete();

      // THEN
      expect(directorFormService.getDirector).toHaveBeenCalled();
      expect(directorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirector>>();
      const director = { id: 'ABC' };
      jest.spyOn(directorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ director });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(directorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

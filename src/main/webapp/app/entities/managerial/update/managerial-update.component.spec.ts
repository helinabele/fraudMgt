import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ManagerialFormService } from './managerial-form.service';
import { ManagerialService } from '../service/managerial.service';
import { IManagerial } from '../managerial.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDirector } from 'app/entities/director/director.model';
import { DirectorService } from 'app/entities/director/service/director.service';

import { ManagerialUpdateComponent } from './managerial-update.component';

describe('Managerial Management Update Component', () => {
  let comp: ManagerialUpdateComponent;
  let fixture: ComponentFixture<ManagerialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let managerialFormService: ManagerialFormService;
  let managerialService: ManagerialService;
  let userService: UserService;
  let directorService: DirectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ManagerialUpdateComponent],
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
      .overrideTemplate(ManagerialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ManagerialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    managerialFormService = TestBed.inject(ManagerialFormService);
    managerialService = TestBed.inject(ManagerialService);
    userService = TestBed.inject(UserService);
    directorService = TestBed.inject(DirectorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const managerial: IManagerial = { id: 'CBA' };
      const user: IUser = { id: '3d51ee8c-eada-4854-b70a-3ec0449c94c5' };
      managerial.user = user;

      const userCollection: IUser[] = [{ id: 'fe8af3a3-6ac4-4b36-a792-6805af5688ff' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managerial });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Director query and add missing value', () => {
      const managerial: IManagerial = { id: 'CBA' };
      const directors: IDirector = { id: 'c50ef17c-becd-42a6-9a1b-44fa5009f899' };
      managerial.directors = directors;

      const directorCollection: IDirector[] = [{ id: 'bbd52cb3-5bd0-4215-a3e2-9f99e32b8c63' }];
      jest.spyOn(directorService, 'query').mockReturnValue(of(new HttpResponse({ body: directorCollection })));
      const additionalDirectors = [directors];
      const expectedCollection: IDirector[] = [...additionalDirectors, ...directorCollection];
      jest.spyOn(directorService, 'addDirectorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managerial });
      comp.ngOnInit();

      expect(directorService.query).toHaveBeenCalled();
      expect(directorService.addDirectorToCollectionIfMissing).toHaveBeenCalledWith(
        directorCollection,
        ...additionalDirectors.map(expect.objectContaining)
      );
      expect(comp.directorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const managerial: IManagerial = { id: 'CBA' };
      const user: IUser = { id: '764ea074-5bb4-4e6a-8c10-2823304b4715' };
      managerial.user = user;
      const directors: IDirector = { id: 'ddf06d74-88ed-4d11-99eb-a85280ce9f97' };
      managerial.directors = directors;

      activatedRoute.data = of({ managerial });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.directorsSharedCollection).toContain(directors);
      expect(comp.managerial).toEqual(managerial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IManagerial>>();
      const managerial = { id: 'ABC' };
      jest.spyOn(managerialFormService, 'getManagerial').mockReturnValue(managerial);
      jest.spyOn(managerialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managerial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managerial }));
      saveSubject.complete();

      // THEN
      expect(managerialFormService.getManagerial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(managerialService.update).toHaveBeenCalledWith(expect.objectContaining(managerial));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IManagerial>>();
      const managerial = { id: 'ABC' };
      jest.spyOn(managerialFormService, 'getManagerial').mockReturnValue({ id: null });
      jest.spyOn(managerialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managerial: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managerial }));
      saveSubject.complete();

      // THEN
      expect(managerialFormService.getManagerial).toHaveBeenCalled();
      expect(managerialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IManagerial>>();
      const managerial = { id: 'ABC' };
      jest.spyOn(managerialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managerial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(managerialService.update).toHaveBeenCalled();
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

    describe('compareDirector', () => {
      it('Should forward to directorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(directorService, 'compareDirector');
        comp.compareDirector(entity, entity2);
        expect(directorService.compareDirector).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

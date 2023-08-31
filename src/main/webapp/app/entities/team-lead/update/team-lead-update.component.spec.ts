import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TeamLeadFormService } from './team-lead-form.service';
import { TeamLeadService } from '../service/team-lead.service';
import { ITeamLead } from '../team-lead.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IManagerial } from 'app/entities/managerial/managerial.model';
import { ManagerialService } from 'app/entities/managerial/service/managerial.service';

import { TeamLeadUpdateComponent } from './team-lead-update.component';

describe('TeamLead Management Update Component', () => {
  let comp: TeamLeadUpdateComponent;
  let fixture: ComponentFixture<TeamLeadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teamLeadFormService: TeamLeadFormService;
  let teamLeadService: TeamLeadService;
  let userService: UserService;
  let managerialService: ManagerialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TeamLeadUpdateComponent],
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
      .overrideTemplate(TeamLeadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeamLeadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teamLeadFormService = TestBed.inject(TeamLeadFormService);
    teamLeadService = TestBed.inject(TeamLeadService);
    userService = TestBed.inject(UserService);
    managerialService = TestBed.inject(ManagerialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const teamLead: ITeamLead = { id: 'CBA' };
      const user: IUser = { id: 'f125f8ea-c664-4e90-81ca-ef65909b92e8' };
      teamLead.user = user;

      const userCollection: IUser[] = [{ id: 'b477a653-588c-4b6f-93bc-39cc976a933a' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamLead });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Managerial query and add missing value', () => {
      const teamLead: ITeamLead = { id: 'CBA' };
      const managers: IManagerial = { id: '5c7621e7-5d8f-4f2c-b4c9-1f95bf00c023' };
      teamLead.managers = managers;

      const managerialCollection: IManagerial[] = [{ id: 'cd93eb50-7df4-4898-b6ed-15d4699d18fc' }];
      jest.spyOn(managerialService, 'query').mockReturnValue(of(new HttpResponse({ body: managerialCollection })));
      const additionalManagerials = [managers];
      const expectedCollection: IManagerial[] = [...additionalManagerials, ...managerialCollection];
      jest.spyOn(managerialService, 'addManagerialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teamLead });
      comp.ngOnInit();

      expect(managerialService.query).toHaveBeenCalled();
      expect(managerialService.addManagerialToCollectionIfMissing).toHaveBeenCalledWith(
        managerialCollection,
        ...additionalManagerials.map(expect.objectContaining)
      );
      expect(comp.managerialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teamLead: ITeamLead = { id: 'CBA' };
      const user: IUser = { id: 'cedd27c5-22e0-4234-a9b0-a8504877a04d' };
      teamLead.user = user;
      const managers: IManagerial = { id: 'eaed6106-e93a-47e6-85d9-410b7fa40bd1' };
      teamLead.managers = managers;

      activatedRoute.data = of({ teamLead });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.managerialsSharedCollection).toContain(managers);
      expect(comp.teamLead).toEqual(teamLead);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamLead>>();
      const teamLead = { id: 'ABC' };
      jest.spyOn(teamLeadFormService, 'getTeamLead').mockReturnValue(teamLead);
      jest.spyOn(teamLeadService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamLead });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamLead }));
      saveSubject.complete();

      // THEN
      expect(teamLeadFormService.getTeamLead).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(teamLeadService.update).toHaveBeenCalledWith(expect.objectContaining(teamLead));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamLead>>();
      const teamLead = { id: 'ABC' };
      jest.spyOn(teamLeadFormService, 'getTeamLead').mockReturnValue({ id: null });
      jest.spyOn(teamLeadService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamLead: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teamLead }));
      saveSubject.complete();

      // THEN
      expect(teamLeadFormService.getTeamLead).toHaveBeenCalled();
      expect(teamLeadService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITeamLead>>();
      const teamLead = { id: 'ABC' };
      jest.spyOn(teamLeadService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teamLead });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teamLeadService.update).toHaveBeenCalled();
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

    describe('compareManagerial', () => {
      it('Should forward to managerialService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(managerialService, 'compareManagerial');
        comp.compareManagerial(entity, entity2);
        expect(managerialService.compareManagerial).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

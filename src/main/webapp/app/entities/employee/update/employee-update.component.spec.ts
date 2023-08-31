import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployeeFormService } from './employee-form.service';
import { EmployeeService } from '../service/employee.service';
import { IEmployee } from '../employee.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDirector } from 'app/entities/director/director.model';
import { DirectorService } from 'app/entities/director/service/director.service';
import { IManagerial } from 'app/entities/managerial/managerial.model';
import { ManagerialService } from 'app/entities/managerial/service/managerial.service';
import { ITeamLead } from 'app/entities/team-lead/team-lead.model';
import { TeamLeadService } from 'app/entities/team-lead/service/team-lead.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';

import { EmployeeUpdateComponent } from './employee-update.component';

describe('Employee Management Update Component', () => {
  let comp: EmployeeUpdateComponent;
  let fixture: ComponentFixture<EmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeFormService: EmployeeFormService;
  let employeeService: EmployeeService;
  let userService: UserService;
  let directorService: DirectorService;
  let managerialService: ManagerialService;
  let teamLeadService: TeamLeadService;
  let teamService: TeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployeeUpdateComponent],
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
      .overrideTemplate(EmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeFormService = TestBed.inject(EmployeeFormService);
    employeeService = TestBed.inject(EmployeeService);
    userService = TestBed.inject(UserService);
    directorService = TestBed.inject(DirectorService);
    managerialService = TestBed.inject(ManagerialService);
    teamLeadService = TestBed.inject(TeamLeadService);
    teamService = TestBed.inject(TeamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const employee: IEmployee = { id: 'CBA' };
      const user: IUser = { id: 'd0ae2780-9611-40fc-b61f-db1ae916867e' };
      employee.user = user;

      const userCollection: IUser[] = [{ id: 'a74170c4-0b2d-4b66-9369-988ef3f5ec85' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Director query and add missing value', () => {
      const employee: IEmployee = { id: 'CBA' };
      const director: IDirector = { id: '49402aa7-9ea0-4319-9b86-94eade1db8ce' };
      employee.director = director;

      const directorCollection: IDirector[] = [{ id: '458be741-3607-449a-8952-46cdddb040a3' }];
      jest.spyOn(directorService, 'query').mockReturnValue(of(new HttpResponse({ body: directorCollection })));
      const additionalDirectors = [director];
      const expectedCollection: IDirector[] = [...additionalDirectors, ...directorCollection];
      jest.spyOn(directorService, 'addDirectorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(directorService.query).toHaveBeenCalled();
      expect(directorService.addDirectorToCollectionIfMissing).toHaveBeenCalledWith(
        directorCollection,
        ...additionalDirectors.map(expect.objectContaining)
      );
      expect(comp.directorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Managerial query and add missing value', () => {
      const employee: IEmployee = { id: 'CBA' };
      const manager: IManagerial = { id: 'bf8df3b5-8002-4892-86c3-a28beb75b65a' };
      employee.manager = manager;

      const managerialCollection: IManagerial[] = [{ id: 'c253a246-a3b7-4b40-8d2b-4607dd66d505' }];
      jest.spyOn(managerialService, 'query').mockReturnValue(of(new HttpResponse({ body: managerialCollection })));
      const additionalManagerials = [manager];
      const expectedCollection: IManagerial[] = [...additionalManagerials, ...managerialCollection];
      jest.spyOn(managerialService, 'addManagerialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(managerialService.query).toHaveBeenCalled();
      expect(managerialService.addManagerialToCollectionIfMissing).toHaveBeenCalledWith(
        managerialCollection,
        ...additionalManagerials.map(expect.objectContaining)
      );
      expect(comp.managerialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TeamLead query and add missing value', () => {
      const employee: IEmployee = { id: 'CBA' };
      const teamLead: ITeamLead = { id: '2e5ea8ce-9d06-4177-8785-ad8cb98988ca' };
      employee.teamLead = teamLead;

      const teamLeadCollection: ITeamLead[] = [{ id: 'ce29b1f9-389f-44c5-9f82-a1dcec683606' }];
      jest.spyOn(teamLeadService, 'query').mockReturnValue(of(new HttpResponse({ body: teamLeadCollection })));
      const additionalTeamLeads = [teamLead];
      const expectedCollection: ITeamLead[] = [...additionalTeamLeads, ...teamLeadCollection];
      jest.spyOn(teamLeadService, 'addTeamLeadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(teamLeadService.query).toHaveBeenCalled();
      expect(teamLeadService.addTeamLeadToCollectionIfMissing).toHaveBeenCalledWith(
        teamLeadCollection,
        ...additionalTeamLeads.map(expect.objectContaining)
      );
      expect(comp.teamLeadsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Team query and add missing value', () => {
      const employee: IEmployee = { id: 'CBA' };
      const team: ITeam = { id: '8496066b-68ea-45dc-ac59-65010dc791d2' };
      employee.team = team;

      const teamCollection: ITeam[] = [{ id: 'c4e1c318-991e-4003-9d98-6dda386d564d' }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [team];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(
        teamCollection,
        ...additionalTeams.map(expect.objectContaining)
      );
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employee: IEmployee = { id: 'CBA' };
      const user: IUser = { id: '25199731-a0f0-4da7-9cca-ad8f0d6af10e' };
      employee.user = user;
      const director: IDirector = { id: '724fe49f-fb02-4356-a464-e91e888f13d8' };
      employee.director = director;
      const manager: IManagerial = { id: '9ad10876-789b-43ce-9945-01a646a3564a' };
      employee.manager = manager;
      const teamLead: ITeamLead = { id: '85837bc0-ada2-4819-81a6-86411b6f22f3' };
      employee.teamLead = teamLead;
      const team: ITeam = { id: 'cf707d81-e0f2-47aa-b8d7-38119b2619a0' };
      employee.team = team;

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.directorsSharedCollection).toContain(director);
      expect(comp.managerialsSharedCollection).toContain(manager);
      expect(comp.teamLeadsSharedCollection).toContain(teamLead);
      expect(comp.teamsSharedCollection).toContain(team);
      expect(comp.employee).toEqual(employee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 'ABC' };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue(employee);
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeService.update).toHaveBeenCalledWith(expect.objectContaining(employee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 'ABC' };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue({ id: null });
      jest.spyOn(employeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(employeeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 'ABC' };
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeService.update).toHaveBeenCalled();
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

    describe('compareManagerial', () => {
      it('Should forward to managerialService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(managerialService, 'compareManagerial');
        comp.compareManagerial(entity, entity2);
        expect(managerialService.compareManagerial).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTeamLead', () => {
      it('Should forward to teamLeadService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(teamLeadService, 'compareTeamLead');
        comp.compareTeamLead(entity, entity2);
        expect(teamLeadService.compareTeamLead).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTeam', () => {
      it('Should forward to teamService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(teamService, 'compareTeam');
        comp.compareTeam(entity, entity2);
        expect(teamService.compareTeam).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

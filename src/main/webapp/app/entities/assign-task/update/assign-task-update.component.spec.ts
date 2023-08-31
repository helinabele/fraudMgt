import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssignTaskFormService } from './assign-task-form.service';
import { AssignTaskService } from '../service/assign-task.service';
import { IAssignTask } from '../assign-task.model';
import { IDirector } from 'app/entities/director/director.model';
import { DirectorService } from 'app/entities/director/service/director.service';
import { IManagerial } from 'app/entities/managerial/managerial.model';
import { ManagerialService } from 'app/entities/managerial/service/managerial.service';
import { ITeamLead } from 'app/entities/team-lead/team-lead.model';
import { TeamLeadService } from 'app/entities/team-lead/service/team-lead.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';
import { ITeam } from 'app/entities/team/team.model';
import { TeamService } from 'app/entities/team/service/team.service';

import { AssignTaskUpdateComponent } from './assign-task-update.component';

describe('AssignTask Management Update Component', () => {
  let comp: AssignTaskUpdateComponent;
  let fixture: ComponentFixture<AssignTaskUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assignTaskFormService: AssignTaskFormService;
  let assignTaskService: AssignTaskService;
  let directorService: DirectorService;
  let managerialService: ManagerialService;
  let teamLeadService: TeamLeadService;
  let employeeService: EmployeeService;
  let taskService: TaskService;
  let teamService: TeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssignTaskUpdateComponent],
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
      .overrideTemplate(AssignTaskUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssignTaskUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assignTaskFormService = TestBed.inject(AssignTaskFormService);
    assignTaskService = TestBed.inject(AssignTaskService);
    directorService = TestBed.inject(DirectorService);
    managerialService = TestBed.inject(ManagerialService);
    teamLeadService = TestBed.inject(TeamLeadService);
    employeeService = TestBed.inject(EmployeeService);
    taskService = TestBed.inject(TaskService);
    teamService = TestBed.inject(TeamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Director query and add missing value', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const director: IDirector = { id: '53dd5e7d-dea8-4bcd-8a83-9a64f7fa4340' };
      assignTask.director = director;

      const directorCollection: IDirector[] = [{ id: '073cb139-607c-4859-8acb-ae0c8d28a3bc' }];
      jest.spyOn(directorService, 'query').mockReturnValue(of(new HttpResponse({ body: directorCollection })));
      const additionalDirectors = [director];
      const expectedCollection: IDirector[] = [...additionalDirectors, ...directorCollection];
      jest.spyOn(directorService, 'addDirectorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(directorService.query).toHaveBeenCalled();
      expect(directorService.addDirectorToCollectionIfMissing).toHaveBeenCalledWith(
        directorCollection,
        ...additionalDirectors.map(expect.objectContaining)
      );
      expect(comp.directorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Managerial query and add missing value', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const manager: IManagerial = { id: '5b2b79ad-7ca9-4e69-89b5-5cdc0293e0da' };
      assignTask.manager = manager;

      const managerialCollection: IManagerial[] = [{ id: 'fb123956-9d04-4627-a488-147140d397bc' }];
      jest.spyOn(managerialService, 'query').mockReturnValue(of(new HttpResponse({ body: managerialCollection })));
      const additionalManagerials = [manager];
      const expectedCollection: IManagerial[] = [...additionalManagerials, ...managerialCollection];
      jest.spyOn(managerialService, 'addManagerialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(managerialService.query).toHaveBeenCalled();
      expect(managerialService.addManagerialToCollectionIfMissing).toHaveBeenCalledWith(
        managerialCollection,
        ...additionalManagerials.map(expect.objectContaining)
      );
      expect(comp.managerialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TeamLead query and add missing value', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const teamLead: ITeamLead = { id: 'b8dde702-a7de-474f-982a-11d552cfd491' };
      assignTask.teamLead = teamLead;

      const teamLeadCollection: ITeamLead[] = [{ id: 'ae3ffe77-b4fd-432a-b554-7ba4e78e27f9' }];
      jest.spyOn(teamLeadService, 'query').mockReturnValue(of(new HttpResponse({ body: teamLeadCollection })));
      const additionalTeamLeads = [teamLead];
      const expectedCollection: ITeamLead[] = [...additionalTeamLeads, ...teamLeadCollection];
      jest.spyOn(teamLeadService, 'addTeamLeadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(teamLeadService.query).toHaveBeenCalled();
      expect(teamLeadService.addTeamLeadToCollectionIfMissing).toHaveBeenCalledWith(
        teamLeadCollection,
        ...additionalTeamLeads.map(expect.objectContaining)
      );
      expect(comp.teamLeadsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const employee: IEmployee = { id: '9cdf10a8-3b37-4c98-94e7-7899bde7cf42' };
      assignTask.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 'ba937da2-6533-42d6-99c4-7a293d11c6ea' }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Task query and add missing value', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const task: ITask = { id: '9ffabade-c605-453e-85b5-9971866db89f' };
      assignTask.task = task;

      const taskCollection: ITask[] = [{ id: '33363598-1b95-426d-9986-4cd3d7f01ebc' }];
      jest.spyOn(taskService, 'query').mockReturnValue(of(new HttpResponse({ body: taskCollection })));
      const additionalTasks = [task];
      const expectedCollection: ITask[] = [...additionalTasks, ...taskCollection];
      jest.spyOn(taskService, 'addTaskToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(taskService.query).toHaveBeenCalled();
      expect(taskService.addTaskToCollectionIfMissing).toHaveBeenCalledWith(
        taskCollection,
        ...additionalTasks.map(expect.objectContaining)
      );
      expect(comp.tasksSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Team query and add missing value', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const team: ITeam = { id: '251b0c02-c5f4-44b4-82bb-290dcf54ee04' };
      assignTask.team = team;

      const teamCollection: ITeam[] = [{ id: '6593cb0e-9c5f-4264-bf1d-e468732e3c3d' }];
      jest.spyOn(teamService, 'query').mockReturnValue(of(new HttpResponse({ body: teamCollection })));
      const additionalTeams = [team];
      const expectedCollection: ITeam[] = [...additionalTeams, ...teamCollection];
      jest.spyOn(teamService, 'addTeamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(teamService.query).toHaveBeenCalled();
      expect(teamService.addTeamToCollectionIfMissing).toHaveBeenCalledWith(
        teamCollection,
        ...additionalTeams.map(expect.objectContaining)
      );
      expect(comp.teamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assignTask: IAssignTask = { id: 'CBA' };
      const director: IDirector = { id: 'a1815945-83f1-48ed-a0dd-49e2a6b2d8d3' };
      assignTask.director = director;
      const manager: IManagerial = { id: '0c288392-cb07-4987-8322-d1692d043cf4' };
      assignTask.manager = manager;
      const teamLead: ITeamLead = { id: '1d0adcd2-5593-4502-af52-157b1be9691a' };
      assignTask.teamLead = teamLead;
      const employee: IEmployee = { id: '9f66c526-5f45-4a6c-851c-a595d5f7dd00' };
      assignTask.employee = employee;
      const task: ITask = { id: 'a28715fc-311d-4472-b0e0-36e037c32f20' };
      assignTask.task = task;
      const team: ITeam = { id: 'ff944a59-679a-438f-ab9e-e90bbcd8fc9b' };
      assignTask.team = team;

      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      expect(comp.directorsSharedCollection).toContain(director);
      expect(comp.managerialsSharedCollection).toContain(manager);
      expect(comp.teamLeadsSharedCollection).toContain(teamLead);
      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.tasksSharedCollection).toContain(task);
      expect(comp.teamsSharedCollection).toContain(team);
      expect(comp.assignTask).toEqual(assignTask);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssignTask>>();
      const assignTask = { id: 'ABC' };
      jest.spyOn(assignTaskFormService, 'getAssignTask').mockReturnValue(assignTask);
      jest.spyOn(assignTaskService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assignTask }));
      saveSubject.complete();

      // THEN
      expect(assignTaskFormService.getAssignTask).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assignTaskService.update).toHaveBeenCalledWith(expect.objectContaining(assignTask));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssignTask>>();
      const assignTask = { id: 'ABC' };
      jest.spyOn(assignTaskFormService, 'getAssignTask').mockReturnValue({ id: null });
      jest.spyOn(assignTaskService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assignTask: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assignTask }));
      saveSubject.complete();

      // THEN
      expect(assignTaskFormService.getAssignTask).toHaveBeenCalled();
      expect(assignTaskService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssignTask>>();
      const assignTask = { id: 'ABC' };
      jest.spyOn(assignTaskService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assignTask });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assignTaskService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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

    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTask', () => {
      it('Should forward to taskService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(taskService, 'compareTask');
        comp.compareTask(entity, entity2);
        expect(taskService.compareTask).toHaveBeenCalledWith(entity, entity2);
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

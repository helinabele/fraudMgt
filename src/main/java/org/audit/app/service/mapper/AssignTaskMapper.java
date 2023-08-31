package org.audit.app.service.mapper;

import org.audit.app.domain.AssignTask;
import org.audit.app.domain.Director;
import org.audit.app.domain.Employee;
import org.audit.app.domain.Managerial;
import org.audit.app.domain.Task;
import org.audit.app.domain.Team;
import org.audit.app.domain.TeamLead;
import org.audit.app.service.dto.AssignTaskDTO;
import org.audit.app.service.dto.DirectorDTO;
import org.audit.app.service.dto.EmployeeDTO;
import org.audit.app.service.dto.ManagerialDTO;
import org.audit.app.service.dto.TaskDTO;
import org.audit.app.service.dto.TeamDTO;
import org.audit.app.service.dto.TeamLeadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssignTask} and its DTO {@link AssignTaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssignTaskMapper extends EntityMapper<AssignTaskDTO, AssignTask> {
    @Mapping(target = "director", source = "director", qualifiedByName = "directorDirectorName")
    @Mapping(target = "manager", source = "manager", qualifiedByName = "managerialManagerialName")
    @Mapping(target = "teamLead", source = "teamLead", qualifiedByName = "teamLeadTeamLeadName")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeName")
    @Mapping(target = "task", source = "task", qualifiedByName = "taskTitle")
    @Mapping(target = "team", source = "team", qualifiedByName = "teamTeamName")
    AssignTaskDTO toDto(AssignTask s);

    @Named("directorDirectorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "directorName", source = "directorName")
    DirectorDTO toDtoDirectorDirectorName(Director director);

    @Named("managerialManagerialName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "managerialName", source = "managerialName")
    ManagerialDTO toDtoManagerialManagerialName(Managerial managerial);

    @Named("teamLeadTeamLeadName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamLeadName", source = "teamLeadName")
    TeamLeadDTO toDtoTeamLeadTeamLeadName(TeamLead teamLead);

    @Named("employeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeDTO toDtoEmployeeName(Employee employee);

    @Named("taskTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TaskDTO toDtoTaskTitle(Task task);

    @Named("teamTeamName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "teamName", source = "teamName")
    TeamDTO toDtoTeamTeamName(Team team);
}

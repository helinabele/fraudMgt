package org.audit.app.service.mapper;

import org.audit.app.domain.Managerial;
import org.audit.app.domain.TeamLead;
import org.audit.app.domain.User;
import org.audit.app.service.dto.ManagerialDTO;
import org.audit.app.service.dto.TeamLeadDTO;
import org.audit.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamLead} and its DTO {@link TeamLeadDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamLeadMapper extends EntityMapper<TeamLeadDTO, TeamLead> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "managers", source = "managers", qualifiedByName = "managerialManagerialName")
    TeamLeadDTO toDto(TeamLead s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("managerialManagerialName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "managerialName", source = "managerialName")
    ManagerialDTO toDtoManagerialManagerialName(Managerial managerial);
}

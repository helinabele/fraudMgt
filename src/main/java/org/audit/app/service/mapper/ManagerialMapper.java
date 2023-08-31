package org.audit.app.service.mapper;

import org.audit.app.domain.Director;
import org.audit.app.domain.Managerial;
import org.audit.app.domain.User;
import org.audit.app.service.dto.DirectorDTO;
import org.audit.app.service.dto.ManagerialDTO;
import org.audit.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Managerial} and its DTO {@link ManagerialDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManagerialMapper extends EntityMapper<ManagerialDTO, Managerial> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "directors", source = "directors", qualifiedByName = "directorDirectorName")
    ManagerialDTO toDto(Managerial s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("directorDirectorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "directorName", source = "directorName")
    DirectorDTO toDtoDirectorDirectorName(Director director);
}

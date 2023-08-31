package org.audit.app.service.mapper;

import org.audit.app.domain.Director;
import org.audit.app.domain.User;
import org.audit.app.service.dto.DirectorDTO;
import org.audit.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Director} and its DTO {@link DirectorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DirectorMapper extends EntityMapper<DirectorDTO, Director> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    DirectorDTO toDto(Director s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}

package org.audit.app.service.mapper;

import org.audit.app.domain.InternalEmployee;
import org.audit.app.service.dto.InternalEmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InternalEmployee} and its DTO {@link InternalEmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface InternalEmployeeMapper extends EntityMapper<InternalEmployeeDTO, InternalEmployee> {}

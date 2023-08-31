package org.audit.app.service.mapper;

import org.audit.app.domain.ExternalEmployee;
import org.audit.app.service.dto.ExternalEmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExternalEmployee} and its DTO {@link ExternalEmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExternalEmployeeMapper extends EntityMapper<ExternalEmployeeDTO, ExternalEmployee> {}

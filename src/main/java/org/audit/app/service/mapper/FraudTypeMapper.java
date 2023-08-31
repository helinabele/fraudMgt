package org.audit.app.service.mapper;

import org.audit.app.domain.FraudType;
import org.audit.app.service.dto.FraudTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FraudType} and its DTO {@link FraudTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FraudTypeMapper extends EntityMapper<FraudTypeDTO, FraudType> {}

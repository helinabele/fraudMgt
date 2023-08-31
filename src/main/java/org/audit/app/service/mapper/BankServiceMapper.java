package org.audit.app.service.mapper;

import org.audit.app.domain.BankService;
import org.audit.app.service.dto.BankServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankService} and its DTO {@link BankServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankServiceMapper extends EntityMapper<BankServiceDTO, BankService> {}

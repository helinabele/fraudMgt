package org.audit.app.service.mapper;

import org.audit.app.domain.BankAccount;
import org.audit.app.service.dto.BankAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccount} and its DTO {@link BankAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {}

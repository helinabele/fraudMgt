package org.audit.app.service.mapper;

import org.audit.app.domain.BankAccount;
import org.audit.app.domain.BankService;
import org.audit.app.domain.Employee;
import org.audit.app.domain.ExternalEmployee;
import org.audit.app.domain.FraudInvestigationReport;
import org.audit.app.domain.FraudKnowledgeManagement;
import org.audit.app.domain.FraudType;
import org.audit.app.domain.InternalEmployee;
import org.audit.app.service.dto.BankAccountDTO;
import org.audit.app.service.dto.BankServiceDTO;
import org.audit.app.service.dto.EmployeeDTO;
import org.audit.app.service.dto.ExternalEmployeeDTO;
import org.audit.app.service.dto.FraudInvestigationReportDTO;
import org.audit.app.service.dto.FraudKnowledgeManagementDTO;
import org.audit.app.service.dto.FraudTypeDTO;
import org.audit.app.service.dto.InternalEmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FraudKnowledgeManagement} and its DTO {@link FraudKnowledgeManagementDTO}.
 */
@Mapper(componentModel = "spring")
public interface FraudKnowledgeManagementMapper extends EntityMapper<FraudKnowledgeManagementDTO, FraudKnowledgeManagement> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeName")
    @Mapping(target = "fraudInvestigationReport", source = "fraudInvestigationReport", qualifiedByName = "fraudInvestigationReportTitle")
    @Mapping(target = "fraudType", source = "fraudType", qualifiedByName = "fraudTypeFraudName")
    @Mapping(target = "bankAccount", source = "bankAccount", qualifiedByName = "bankAccountBankName")
    @Mapping(target = "bankService", source = "bankService", qualifiedByName = "bankServiceServiceName")
    @Mapping(target = "internalEmployee", source = "internalEmployee", qualifiedByName = "internalEmployeeName")
    @Mapping(target = "externalEmployee", source = "externalEmployee", qualifiedByName = "externalEmployeeName")
    FraudKnowledgeManagementDTO toDto(FraudKnowledgeManagement s);

    @Named("employeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeDTO toDtoEmployeeName(Employee employee);

    @Named("fraudInvestigationReportTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    FraudInvestigationReportDTO toDtoFraudInvestigationReportTitle(FraudInvestigationReport fraudInvestigationReport);

    @Named("fraudTypeFraudName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fraudName", source = "fraudName")
    FraudTypeDTO toDtoFraudTypeFraudName(FraudType fraudType);

    @Named("bankAccountBankName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "bankName", source = "bankName")
    BankAccountDTO toDtoBankAccountBankName(BankAccount bankAccount);

    @Named("bankServiceServiceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "serviceName", source = "serviceName")
    BankServiceDTO toDtoBankServiceServiceName(BankService bankService);

    @Named("internalEmployeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    InternalEmployeeDTO toDtoInternalEmployeeName(InternalEmployee internalEmployee);

    @Named("externalEmployeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ExternalEmployeeDTO toDtoExternalEmployeeName(ExternalEmployee externalEmployee);
}

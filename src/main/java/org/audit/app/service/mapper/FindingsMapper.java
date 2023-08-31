package org.audit.app.service.mapper;

import org.audit.app.domain.Findings;
import org.audit.app.domain.FraudInvestigationReport;
import org.audit.app.service.dto.FindingsDTO;
import org.audit.app.service.dto.FraudInvestigationReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Findings} and its DTO {@link FindingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface FindingsMapper extends EntityMapper<FindingsDTO, Findings> {
    @Mapping(target = "fraudInvestigationReport", source = "fraudInvestigationReport", qualifiedByName = "fraudInvestigationReportId")
    FindingsDTO toDto(Findings s);

    @Named("fraudInvestigationReportId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FraudInvestigationReportDTO toDtoFraudInvestigationReportId(FraudInvestigationReport fraudInvestigationReport);
}

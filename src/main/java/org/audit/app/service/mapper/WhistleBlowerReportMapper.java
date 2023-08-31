package org.audit.app.service.mapper;

import org.audit.app.domain.WhistleBlowerReport;
import org.audit.app.service.dto.WhistleBlowerReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WhistleBlowerReport} and its DTO {@link WhistleBlowerReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface WhistleBlowerReportMapper extends EntityMapper<WhistleBlowerReportDTO, WhistleBlowerReport> {}

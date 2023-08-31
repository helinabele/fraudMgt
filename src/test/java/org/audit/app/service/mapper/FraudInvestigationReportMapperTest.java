package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FraudInvestigationReportMapperTest {

    private FraudInvestigationReportMapper fraudInvestigationReportMapper;

    @BeforeEach
    public void setUp() {
        fraudInvestigationReportMapper = new FraudInvestigationReportMapperImpl();
    }
}

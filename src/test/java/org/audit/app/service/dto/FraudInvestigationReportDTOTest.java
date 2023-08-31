package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudInvestigationReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudInvestigationReportDTO.class);
        FraudInvestigationReportDTO fraudInvestigationReportDTO1 = new FraudInvestigationReportDTO();
        fraudInvestigationReportDTO1.setId("id1");
        FraudInvestigationReportDTO fraudInvestigationReportDTO2 = new FraudInvestigationReportDTO();
        assertThat(fraudInvestigationReportDTO1).isNotEqualTo(fraudInvestigationReportDTO2);
        fraudInvestigationReportDTO2.setId(fraudInvestigationReportDTO1.getId());
        assertThat(fraudInvestigationReportDTO1).isEqualTo(fraudInvestigationReportDTO2);
        fraudInvestigationReportDTO2.setId("id2");
        assertThat(fraudInvestigationReportDTO1).isNotEqualTo(fraudInvestigationReportDTO2);
        fraudInvestigationReportDTO1.setId(null);
        assertThat(fraudInvestigationReportDTO1).isNotEqualTo(fraudInvestigationReportDTO2);
    }
}

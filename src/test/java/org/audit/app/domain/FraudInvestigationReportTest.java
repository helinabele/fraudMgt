package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudInvestigationReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudInvestigationReport.class);
        FraudInvestigationReport fraudInvestigationReport1 = new FraudInvestigationReport();
        fraudInvestigationReport1.setId("id1");
        FraudInvestigationReport fraudInvestigationReport2 = new FraudInvestigationReport();
        fraudInvestigationReport2.setId(fraudInvestigationReport1.getId());
        assertThat(fraudInvestigationReport1).isEqualTo(fraudInvestigationReport2);
        fraudInvestigationReport2.setId("id2");
        assertThat(fraudInvestigationReport1).isNotEqualTo(fraudInvestigationReport2);
        fraudInvestigationReport1.setId(null);
        assertThat(fraudInvestigationReport1).isNotEqualTo(fraudInvestigationReport2);
    }
}

package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WhistleBlowerReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhistleBlowerReport.class);
        WhistleBlowerReport whistleBlowerReport1 = new WhistleBlowerReport();
        whistleBlowerReport1.setId("id1");
        WhistleBlowerReport whistleBlowerReport2 = new WhistleBlowerReport();
        whistleBlowerReport2.setId(whistleBlowerReport1.getId());
        assertThat(whistleBlowerReport1).isEqualTo(whistleBlowerReport2);
        whistleBlowerReport2.setId("id2");
        assertThat(whistleBlowerReport1).isNotEqualTo(whistleBlowerReport2);
        whistleBlowerReport1.setId(null);
        assertThat(whistleBlowerReport1).isNotEqualTo(whistleBlowerReport2);
    }
}

package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WhistleBlowerReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhistleBlowerReportDTO.class);
        WhistleBlowerReportDTO whistleBlowerReportDTO1 = new WhistleBlowerReportDTO();
        whistleBlowerReportDTO1.setId("id1");
        WhistleBlowerReportDTO whistleBlowerReportDTO2 = new WhistleBlowerReportDTO();
        assertThat(whistleBlowerReportDTO1).isNotEqualTo(whistleBlowerReportDTO2);
        whistleBlowerReportDTO2.setId(whistleBlowerReportDTO1.getId());
        assertThat(whistleBlowerReportDTO1).isEqualTo(whistleBlowerReportDTO2);
        whistleBlowerReportDTO2.setId("id2");
        assertThat(whistleBlowerReportDTO1).isNotEqualTo(whistleBlowerReportDTO2);
        whistleBlowerReportDTO1.setId(null);
        assertThat(whistleBlowerReportDTO1).isNotEqualTo(whistleBlowerReportDTO2);
    }
}

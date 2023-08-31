package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamLeadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamLeadDTO.class);
        TeamLeadDTO teamLeadDTO1 = new TeamLeadDTO();
        teamLeadDTO1.setId("id1");
        TeamLeadDTO teamLeadDTO2 = new TeamLeadDTO();
        assertThat(teamLeadDTO1).isNotEqualTo(teamLeadDTO2);
        teamLeadDTO2.setId(teamLeadDTO1.getId());
        assertThat(teamLeadDTO1).isEqualTo(teamLeadDTO2);
        teamLeadDTO2.setId("id2");
        assertThat(teamLeadDTO1).isNotEqualTo(teamLeadDTO2);
        teamLeadDTO1.setId(null);
        assertThat(teamLeadDTO1).isNotEqualTo(teamLeadDTO2);
    }
}

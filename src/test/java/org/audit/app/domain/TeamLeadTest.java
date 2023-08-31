package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamLeadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamLead.class);
        TeamLead teamLead1 = new TeamLead();
        teamLead1.setId("id1");
        TeamLead teamLead2 = new TeamLead();
        teamLead2.setId(teamLead1.getId());
        assertThat(teamLead1).isEqualTo(teamLead2);
        teamLead2.setId("id2");
        assertThat(teamLead1).isNotEqualTo(teamLead2);
        teamLead1.setId(null);
        assertThat(teamLead1).isNotEqualTo(teamLead2);
    }
}

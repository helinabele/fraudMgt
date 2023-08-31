package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FindingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FindingsDTO.class);
        FindingsDTO findingsDTO1 = new FindingsDTO();
        findingsDTO1.setId("id1");
        FindingsDTO findingsDTO2 = new FindingsDTO();
        assertThat(findingsDTO1).isNotEqualTo(findingsDTO2);
        findingsDTO2.setId(findingsDTO1.getId());
        assertThat(findingsDTO1).isEqualTo(findingsDTO2);
        findingsDTO2.setId("id2");
        assertThat(findingsDTO1).isNotEqualTo(findingsDTO2);
        findingsDTO1.setId(null);
        assertThat(findingsDTO1).isNotEqualTo(findingsDTO2);
    }
}

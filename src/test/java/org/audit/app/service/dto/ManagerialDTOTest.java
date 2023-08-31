package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagerialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagerialDTO.class);
        ManagerialDTO managerialDTO1 = new ManagerialDTO();
        managerialDTO1.setId("id1");
        ManagerialDTO managerialDTO2 = new ManagerialDTO();
        assertThat(managerialDTO1).isNotEqualTo(managerialDTO2);
        managerialDTO2.setId(managerialDTO1.getId());
        assertThat(managerialDTO1).isEqualTo(managerialDTO2);
        managerialDTO2.setId("id2");
        assertThat(managerialDTO1).isNotEqualTo(managerialDTO2);
        managerialDTO1.setId(null);
        assertThat(managerialDTO1).isNotEqualTo(managerialDTO2);
    }
}

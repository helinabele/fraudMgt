package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExternalEmployeeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalEmployeeDTO.class);
        ExternalEmployeeDTO externalEmployeeDTO1 = new ExternalEmployeeDTO();
        externalEmployeeDTO1.setId("id1");
        ExternalEmployeeDTO externalEmployeeDTO2 = new ExternalEmployeeDTO();
        assertThat(externalEmployeeDTO1).isNotEqualTo(externalEmployeeDTO2);
        externalEmployeeDTO2.setId(externalEmployeeDTO1.getId());
        assertThat(externalEmployeeDTO1).isEqualTo(externalEmployeeDTO2);
        externalEmployeeDTO2.setId("id2");
        assertThat(externalEmployeeDTO1).isNotEqualTo(externalEmployeeDTO2);
        externalEmployeeDTO1.setId(null);
        assertThat(externalEmployeeDTO1).isNotEqualTo(externalEmployeeDTO2);
    }
}

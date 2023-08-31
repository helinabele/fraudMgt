package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InternalEmployeeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalEmployeeDTO.class);
        InternalEmployeeDTO internalEmployeeDTO1 = new InternalEmployeeDTO();
        internalEmployeeDTO1.setId("id1");
        InternalEmployeeDTO internalEmployeeDTO2 = new InternalEmployeeDTO();
        assertThat(internalEmployeeDTO1).isNotEqualTo(internalEmployeeDTO2);
        internalEmployeeDTO2.setId(internalEmployeeDTO1.getId());
        assertThat(internalEmployeeDTO1).isEqualTo(internalEmployeeDTO2);
        internalEmployeeDTO2.setId("id2");
        assertThat(internalEmployeeDTO1).isNotEqualTo(internalEmployeeDTO2);
        internalEmployeeDTO1.setId(null);
        assertThat(internalEmployeeDTO1).isNotEqualTo(internalEmployeeDTO2);
    }
}

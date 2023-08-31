package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudTypeDTO.class);
        FraudTypeDTO fraudTypeDTO1 = new FraudTypeDTO();
        fraudTypeDTO1.setId("id1");
        FraudTypeDTO fraudTypeDTO2 = new FraudTypeDTO();
        assertThat(fraudTypeDTO1).isNotEqualTo(fraudTypeDTO2);
        fraudTypeDTO2.setId(fraudTypeDTO1.getId());
        assertThat(fraudTypeDTO1).isEqualTo(fraudTypeDTO2);
        fraudTypeDTO2.setId("id2");
        assertThat(fraudTypeDTO1).isNotEqualTo(fraudTypeDTO2);
        fraudTypeDTO1.setId(null);
        assertThat(fraudTypeDTO1).isNotEqualTo(fraudTypeDTO2);
    }
}

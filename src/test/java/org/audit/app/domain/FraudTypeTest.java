package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudType.class);
        FraudType fraudType1 = new FraudType();
        fraudType1.setId("id1");
        FraudType fraudType2 = new FraudType();
        fraudType2.setId(fraudType1.getId());
        assertThat(fraudType1).isEqualTo(fraudType2);
        fraudType2.setId("id2");
        assertThat(fraudType1).isNotEqualTo(fraudType2);
        fraudType1.setId(null);
        assertThat(fraudType1).isNotEqualTo(fraudType2);
    }
}

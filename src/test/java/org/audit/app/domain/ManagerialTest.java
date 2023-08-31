package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagerialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Managerial.class);
        Managerial managerial1 = new Managerial();
        managerial1.setId("id1");
        Managerial managerial2 = new Managerial();
        managerial2.setId(managerial1.getId());
        assertThat(managerial1).isEqualTo(managerial2);
        managerial2.setId("id2");
        assertThat(managerial1).isNotEqualTo(managerial2);
        managerial1.setId(null);
        assertThat(managerial1).isNotEqualTo(managerial2);
    }
}

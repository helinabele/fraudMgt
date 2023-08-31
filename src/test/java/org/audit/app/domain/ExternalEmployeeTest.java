package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExternalEmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalEmployee.class);
        ExternalEmployee externalEmployee1 = new ExternalEmployee();
        externalEmployee1.setId("id1");
        ExternalEmployee externalEmployee2 = new ExternalEmployee();
        externalEmployee2.setId(externalEmployee1.getId());
        assertThat(externalEmployee1).isEqualTo(externalEmployee2);
        externalEmployee2.setId("id2");
        assertThat(externalEmployee1).isNotEqualTo(externalEmployee2);
        externalEmployee1.setId(null);
        assertThat(externalEmployee1).isNotEqualTo(externalEmployee2);
    }
}

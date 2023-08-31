package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InternalEmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalEmployee.class);
        InternalEmployee internalEmployee1 = new InternalEmployee();
        internalEmployee1.setId("id1");
        InternalEmployee internalEmployee2 = new InternalEmployee();
        internalEmployee2.setId(internalEmployee1.getId());
        assertThat(internalEmployee1).isEqualTo(internalEmployee2);
        internalEmployee2.setId("id2");
        assertThat(internalEmployee1).isNotEqualTo(internalEmployee2);
        internalEmployee1.setId(null);
        assertThat(internalEmployee1).isNotEqualTo(internalEmployee2);
    }
}

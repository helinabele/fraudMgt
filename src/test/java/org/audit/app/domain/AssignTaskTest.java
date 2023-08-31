package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssignTaskTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignTask.class);
        AssignTask assignTask1 = new AssignTask();
        assignTask1.setId("id1");
        AssignTask assignTask2 = new AssignTask();
        assignTask2.setId(assignTask1.getId());
        assertThat(assignTask1).isEqualTo(assignTask2);
        assignTask2.setId("id2");
        assertThat(assignTask1).isNotEqualTo(assignTask2);
        assignTask1.setId(null);
        assertThat(assignTask1).isNotEqualTo(assignTask2);
    }
}

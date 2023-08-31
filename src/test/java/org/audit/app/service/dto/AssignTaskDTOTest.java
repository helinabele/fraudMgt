package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssignTaskDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignTaskDTO.class);
        AssignTaskDTO assignTaskDTO1 = new AssignTaskDTO();
        assignTaskDTO1.setId("id1");
        AssignTaskDTO assignTaskDTO2 = new AssignTaskDTO();
        assertThat(assignTaskDTO1).isNotEqualTo(assignTaskDTO2);
        assignTaskDTO2.setId(assignTaskDTO1.getId());
        assertThat(assignTaskDTO1).isEqualTo(assignTaskDTO2);
        assignTaskDTO2.setId("id2");
        assertThat(assignTaskDTO1).isNotEqualTo(assignTaskDTO2);
        assignTaskDTO1.setId(null);
        assertThat(assignTaskDTO1).isNotEqualTo(assignTaskDTO2);
    }
}

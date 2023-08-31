package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudKnowledgeManagementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudKnowledgeManagementDTO.class);
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO1 = new FraudKnowledgeManagementDTO();
        fraudKnowledgeManagementDTO1.setId("id1");
        FraudKnowledgeManagementDTO fraudKnowledgeManagementDTO2 = new FraudKnowledgeManagementDTO();
        assertThat(fraudKnowledgeManagementDTO1).isNotEqualTo(fraudKnowledgeManagementDTO2);
        fraudKnowledgeManagementDTO2.setId(fraudKnowledgeManagementDTO1.getId());
        assertThat(fraudKnowledgeManagementDTO1).isEqualTo(fraudKnowledgeManagementDTO2);
        fraudKnowledgeManagementDTO2.setId("id2");
        assertThat(fraudKnowledgeManagementDTO1).isNotEqualTo(fraudKnowledgeManagementDTO2);
        fraudKnowledgeManagementDTO1.setId(null);
        assertThat(fraudKnowledgeManagementDTO1).isNotEqualTo(fraudKnowledgeManagementDTO2);
    }
}

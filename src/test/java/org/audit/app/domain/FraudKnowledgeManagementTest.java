package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudKnowledgeManagementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudKnowledgeManagement.class);
        FraudKnowledgeManagement fraudKnowledgeManagement1 = new FraudKnowledgeManagement();
        fraudKnowledgeManagement1.setId("id1");
        FraudKnowledgeManagement fraudKnowledgeManagement2 = new FraudKnowledgeManagement();
        fraudKnowledgeManagement2.setId(fraudKnowledgeManagement1.getId());
        assertThat(fraudKnowledgeManagement1).isEqualTo(fraudKnowledgeManagement2);
        fraudKnowledgeManagement2.setId("id2");
        assertThat(fraudKnowledgeManagement1).isNotEqualTo(fraudKnowledgeManagement2);
        fraudKnowledgeManagement1.setId(null);
        assertThat(fraudKnowledgeManagement1).isNotEqualTo(fraudKnowledgeManagement2);
    }
}

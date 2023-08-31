package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FraudKnowledgeManagementMapperTest {

    private FraudKnowledgeManagementMapper fraudKnowledgeManagementMapper;

    @BeforeEach
    public void setUp() {
        fraudKnowledgeManagementMapper = new FraudKnowledgeManagementMapperImpl();
    }
}

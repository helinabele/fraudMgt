package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamLeadMapperTest {

    private TeamLeadMapper teamLeadMapper;

    @BeforeEach
    public void setUp() {
        teamLeadMapper = new TeamLeadMapperImpl();
    }
}

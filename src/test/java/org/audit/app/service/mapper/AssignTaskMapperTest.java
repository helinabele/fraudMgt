package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignTaskMapperTest {

    private AssignTaskMapper assignTaskMapper;

    @BeforeEach
    public void setUp() {
        assignTaskMapper = new AssignTaskMapperImpl();
    }
}

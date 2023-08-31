package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerialMapperTest {

    private ManagerialMapper managerialMapper;

    @BeforeEach
    public void setUp() {
        managerialMapper = new ManagerialMapperImpl();
    }
}

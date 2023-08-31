package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExternalEmployeeMapperTest {

    private ExternalEmployeeMapper externalEmployeeMapper;

    @BeforeEach
    public void setUp() {
        externalEmployeeMapper = new ExternalEmployeeMapperImpl();
    }
}

package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternalEmployeeMapperTest {

    private InternalEmployeeMapper internalEmployeeMapper;

    @BeforeEach
    public void setUp() {
        internalEmployeeMapper = new InternalEmployeeMapperImpl();
    }
}

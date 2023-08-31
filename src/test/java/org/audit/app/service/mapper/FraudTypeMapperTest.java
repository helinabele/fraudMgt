package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FraudTypeMapperTest {

    private FraudTypeMapper fraudTypeMapper;

    @BeforeEach
    public void setUp() {
        fraudTypeMapper = new FraudTypeMapperImpl();
    }
}

package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindingsMapperTest {

    private FindingsMapper findingsMapper;

    @BeforeEach
    public void setUp() {
        findingsMapper = new FindingsMapperImpl();
    }
}

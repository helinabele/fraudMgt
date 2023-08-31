package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectorMapperTest {

    private DirectorMapper directorMapper;

    @BeforeEach
    public void setUp() {
        directorMapper = new DirectorMapperImpl();
    }
}

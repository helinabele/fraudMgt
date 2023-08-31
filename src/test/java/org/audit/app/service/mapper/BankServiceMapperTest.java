package org.audit.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankServiceMapperTest {

    private BankServiceMapper bankServiceMapper;

    @BeforeEach
    public void setUp() {
        bankServiceMapper = new BankServiceMapperImpl();
    }
}

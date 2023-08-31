package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankService.class);
        BankService bankService1 = new BankService();
        bankService1.setId("id1");
        BankService bankService2 = new BankService();
        bankService2.setId(bankService1.getId());
        assertThat(bankService1).isEqualTo(bankService2);
        bankService2.setId("id2");
        assertThat(bankService1).isNotEqualTo(bankService2);
        bankService1.setId(null);
        assertThat(bankService1).isNotEqualTo(bankService2);
    }
}

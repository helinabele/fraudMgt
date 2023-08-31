package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankServiceDTO.class);
        BankServiceDTO bankServiceDTO1 = new BankServiceDTO();
        bankServiceDTO1.setId("id1");
        BankServiceDTO bankServiceDTO2 = new BankServiceDTO();
        assertThat(bankServiceDTO1).isNotEqualTo(bankServiceDTO2);
        bankServiceDTO2.setId(bankServiceDTO1.getId());
        assertThat(bankServiceDTO1).isEqualTo(bankServiceDTO2);
        bankServiceDTO2.setId("id2");
        assertThat(bankServiceDTO1).isNotEqualTo(bankServiceDTO2);
        bankServiceDTO1.setId(null);
        assertThat(bankServiceDTO1).isNotEqualTo(bankServiceDTO2);
    }
}

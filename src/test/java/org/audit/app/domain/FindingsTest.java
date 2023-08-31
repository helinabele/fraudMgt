package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FindingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Findings.class);
        Findings findings1 = new Findings();
        findings1.setId("id1");
        Findings findings2 = new Findings();
        findings2.setId(findings1.getId());
        assertThat(findings1).isEqualTo(findings2);
        findings2.setId("id2");
        assertThat(findings1).isNotEqualTo(findings2);
        findings1.setId(null);
        assertThat(findings1).isNotEqualTo(findings2);
    }
}

package org.audit.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DirectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Director.class);
        Director director1 = new Director();
        director1.setId("id1");
        Director director2 = new Director();
        director2.setId(director1.getId());
        assertThat(director1).isEqualTo(director2);
        director2.setId("id2");
        assertThat(director1).isNotEqualTo(director2);
        director1.setId(null);
        assertThat(director1).isNotEqualTo(director2);
    }
}

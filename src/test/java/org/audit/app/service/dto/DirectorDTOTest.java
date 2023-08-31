package org.audit.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.audit.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DirectorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectorDTO.class);
        DirectorDTO directorDTO1 = new DirectorDTO();
        directorDTO1.setId("id1");
        DirectorDTO directorDTO2 = new DirectorDTO();
        assertThat(directorDTO1).isNotEqualTo(directorDTO2);
        directorDTO2.setId(directorDTO1.getId());
        assertThat(directorDTO1).isEqualTo(directorDTO2);
        directorDTO2.setId("id2");
        assertThat(directorDTO1).isNotEqualTo(directorDTO2);
        directorDTO1.setId(null);
        assertThat(directorDTO1).isNotEqualTo(directorDTO2);
    }
}

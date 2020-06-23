package com.pasqualeb.prima.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pasqualeb.prima.web.rest.TestUtil;

public class CustomerHostDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerHostDTO.class);
        CustomerHostDTO customerHostDTO1 = new CustomerHostDTO();
        customerHostDTO1.setId(1L);
        CustomerHostDTO customerHostDTO2 = new CustomerHostDTO();
        assertThat(customerHostDTO1).isNotEqualTo(customerHostDTO2);
        customerHostDTO2.setId(customerHostDTO1.getId());
        assertThat(customerHostDTO1).isEqualTo(customerHostDTO2);
        customerHostDTO2.setId(2L);
        assertThat(customerHostDTO1).isNotEqualTo(customerHostDTO2);
        customerHostDTO1.setId(null);
        assertThat(customerHostDTO1).isNotEqualTo(customerHostDTO2);
    }
}

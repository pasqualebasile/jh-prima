package com.pasqualeb.prima.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pasqualeb.prima.web.rest.TestUtil;

public class CustomerHostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerHost.class);
        CustomerHost customerHost1 = new CustomerHost();
        customerHost1.setId(1L);
        CustomerHost customerHost2 = new CustomerHost();
        customerHost2.setId(customerHost1.getId());
        assertThat(customerHost1).isEqualTo(customerHost2);
        customerHost2.setId(2L);
        assertThat(customerHost1).isNotEqualTo(customerHost2);
        customerHost1.setId(null);
        assertThat(customerHost1).isNotEqualTo(customerHost2);
    }
}

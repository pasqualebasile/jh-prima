package com.pasqualeb.prima.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerHostMapperTest {

    private CustomerHostMapper customerHostMapper;

    @BeforeEach
    public void setUp() {
        customerHostMapper = new CustomerHostMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerHostMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerHostMapper.fromId(null)).isNull();
    }
}

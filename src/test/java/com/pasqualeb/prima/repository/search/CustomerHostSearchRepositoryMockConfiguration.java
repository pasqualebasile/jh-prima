package com.pasqualeb.prima.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CustomerHostSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CustomerHostSearchRepositoryMockConfiguration {

    @MockBean
    private CustomerHostSearchRepository mockCustomerHostSearchRepository;

}

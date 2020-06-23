package com.pasqualeb.prima.repository.search;

import com.pasqualeb.prima.domain.CustomerHost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CustomerHost} entity.
 */
public interface CustomerHostSearchRepository extends ElasticsearchRepository<CustomerHost, Long> {
}

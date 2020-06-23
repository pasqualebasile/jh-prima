package com.pasqualeb.prima.repository.search;

import com.pasqualeb.prima.domain.Card;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Card} entity.
 */
public interface CardSearchRepository extends ElasticsearchRepository<Card, Long> {
}

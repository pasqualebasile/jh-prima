package com.pasqualeb.prima.service.impl;

import com.pasqualeb.prima.service.CustomerHostService;
import com.pasqualeb.prima.domain.CustomerHost;
import com.pasqualeb.prima.repository.CustomerHostRepository;
import com.pasqualeb.prima.repository.search.CustomerHostSearchRepository;
import com.pasqualeb.prima.service.dto.CustomerHostDTO;
import com.pasqualeb.prima.service.mapper.CustomerHostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CustomerHost}.
 */
@Service
@Transactional
public class CustomerHostServiceImpl implements CustomerHostService {

    private final Logger log = LoggerFactory.getLogger(CustomerHostServiceImpl.class);

    private final CustomerHostRepository customerHostRepository;

    private final CustomerHostMapper customerHostMapper;

    private final CustomerHostSearchRepository customerHostSearchRepository;

    public CustomerHostServiceImpl(CustomerHostRepository customerHostRepository, CustomerHostMapper customerHostMapper, CustomerHostSearchRepository customerHostSearchRepository) {
        this.customerHostRepository = customerHostRepository;
        this.customerHostMapper = customerHostMapper;
        this.customerHostSearchRepository = customerHostSearchRepository;
    }

    /**
     * Save a customerHost.
     *
     * @param customerHostDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerHostDTO save(CustomerHostDTO customerHostDTO) {
        log.debug("Request to save CustomerHost : {}", customerHostDTO);
        CustomerHost customerHost = customerHostMapper.toEntity(customerHostDTO);
        customerHost = customerHostRepository.save(customerHost);
        CustomerHostDTO result = customerHostMapper.toDto(customerHost);
        customerHostSearchRepository.save(customerHost);
        return result;
    }

    /**
     * Get all the customerHosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerHostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerHosts");
        return customerHostRepository.findAll(pageable)
            .map(customerHostMapper::toDto);
    }


    /**
     * Get one customerHost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerHostDTO> findOne(Long id) {
        log.debug("Request to get CustomerHost : {}", id);
        return customerHostRepository.findById(id)
            .map(customerHostMapper::toDto);
    }

    /**
     * Delete the customerHost by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerHost : {}", id);
        customerHostRepository.deleteById(id);
        customerHostSearchRepository.deleteById(id);
    }

    /**
     * Search for the customerHost corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerHostDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CustomerHosts for query {}", query);
        return customerHostSearchRepository.search(queryStringQuery(query), pageable)
            .map(customerHostMapper::toDto);
    }
}

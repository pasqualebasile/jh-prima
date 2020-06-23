package com.pasqualeb.prima.service;

import com.pasqualeb.prima.service.dto.CustomerHostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pasqualeb.prima.domain.CustomerHost}.
 */
public interface CustomerHostService {

    /**
     * Save a customerHost.
     *
     * @param customerHostDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerHostDTO save(CustomerHostDTO customerHostDTO);

    /**
     * Get all the customerHosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerHostDTO> findAll(Pageable pageable);


    /**
     * Get the "id" customerHost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerHostDTO> findOne(Long id);

    /**
     * Delete the "id" customerHost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the customerHost corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerHostDTO> search(String query, Pageable pageable);
}

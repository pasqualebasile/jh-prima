package com.pasqualeb.prima.web.rest;

import com.pasqualeb.prima.service.CustomerHostService;
import com.pasqualeb.prima.web.rest.errors.BadRequestAlertException;
import com.pasqualeb.prima.service.dto.CustomerHostDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.pasqualeb.prima.domain.CustomerHost}.
 */
@RestController
@RequestMapping("/api")
public class CustomerHostResource {

    private final Logger log = LoggerFactory.getLogger(CustomerHostResource.class);

    private static final String ENTITY_NAME = "jhPrimaCustomerHost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerHostService customerHostService;

    public CustomerHostResource(CustomerHostService customerHostService) {
        this.customerHostService = customerHostService;
    }

    /**
     * {@code POST  /customer-hosts} : Create a new customerHost.
     *
     * @param customerHostDTO the customerHostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerHostDTO, or with status {@code 400 (Bad Request)} if the customerHost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-hosts")
    public ResponseEntity<CustomerHostDTO> createCustomerHost(@RequestBody CustomerHostDTO customerHostDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerHost : {}", customerHostDTO);
        if (customerHostDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerHost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerHostDTO result = customerHostService.save(customerHostDTO);
        return ResponseEntity.created(new URI("/api/customer-hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-hosts} : Updates an existing customerHost.
     *
     * @param customerHostDTO the customerHostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerHostDTO,
     * or with status {@code 400 (Bad Request)} if the customerHostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerHostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-hosts")
    public ResponseEntity<CustomerHostDTO> updateCustomerHost(@RequestBody CustomerHostDTO customerHostDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerHost : {}", customerHostDTO);
        if (customerHostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerHostDTO result = customerHostService.save(customerHostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerHostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-hosts} : get all the customerHosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerHosts in body.
     */
    @GetMapping("/customer-hosts")
    public ResponseEntity<List<CustomerHostDTO>> getAllCustomerHosts(Pageable pageable) {
        log.debug("REST request to get a page of CustomerHosts");
        Page<CustomerHostDTO> page = customerHostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-hosts/:id} : get the "id" customerHost.
     *
     * @param id the id of the customerHostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerHostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-hosts/{id}")
    public ResponseEntity<CustomerHostDTO> getCustomerHost(@PathVariable Long id) {
        log.debug("REST request to get CustomerHost : {}", id);
        Optional<CustomerHostDTO> customerHostDTO = customerHostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerHostDTO);
    }

    /**
     * {@code DELETE  /customer-hosts/:id} : delete the "id" customerHost.
     *
     * @param id the id of the customerHostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-hosts/{id}")
    public ResponseEntity<Void> deleteCustomerHost(@PathVariable Long id) {
        log.debug("REST request to delete CustomerHost : {}", id);
        customerHostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/customer-hosts?query=:query} : search for the customerHost corresponding
     * to the query.
     *
     * @param query the query of the customerHost search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/customer-hosts")
    public ResponseEntity<List<CustomerHostDTO>> searchCustomerHosts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CustomerHosts for query {}", query);
        Page<CustomerHostDTO> page = customerHostService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}

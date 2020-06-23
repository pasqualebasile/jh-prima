package com.pasqualeb.prima.web.rest;

import com.pasqualeb.prima.JhPrimaApp;
import com.pasqualeb.prima.domain.CustomerHost;
import com.pasqualeb.prima.repository.CustomerHostRepository;
import com.pasqualeb.prima.repository.search.CustomerHostSearchRepository;
import com.pasqualeb.prima.service.CustomerHostService;
import com.pasqualeb.prima.service.dto.CustomerHostDTO;
import com.pasqualeb.prima.service.mapper.CustomerHostMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerHostResource} REST controller.
 */
@SpringBootTest(classes = JhPrimaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerHostResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    @Autowired
    private CustomerHostRepository customerHostRepository;

    @Autowired
    private CustomerHostMapper customerHostMapper;

    @Autowired
    private CustomerHostService customerHostService;

    /**
     * This repository is mocked in the com.pasqualeb.prima.repository.search test package.
     *
     * @see com.pasqualeb.prima.repository.search.CustomerHostSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerHostSearchRepository mockCustomerHostSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerHostMockMvc;

    private CustomerHost customerHost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerHost createEntity(EntityManager em) {
        CustomerHost customerHost = new CustomerHost()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME);
        return customerHost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerHost createUpdatedEntity(EntityManager em) {
        CustomerHost customerHost = new CustomerHost()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME);
        return customerHost;
    }

    @BeforeEach
    public void initTest() {
        customerHost = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerHost() throws Exception {
        int databaseSizeBeforeCreate = customerHostRepository.findAll().size();
        // Create the CustomerHost
        CustomerHostDTO customerHostDTO = customerHostMapper.toDto(customerHost);
        restCustomerHostMockMvc.perform(post("/api/customer-hosts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerHostDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerHost in the database
        List<CustomerHost> customerHostList = customerHostRepository.findAll();
        assertThat(customerHostList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerHost testCustomerHost = customerHostList.get(customerHostList.size() - 1);
        assertThat(testCustomerHost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerHost.getSurname()).isEqualTo(DEFAULT_SURNAME);

        // Validate the CustomerHost in Elasticsearch
        verify(mockCustomerHostSearchRepository, times(1)).save(testCustomerHost);
    }

    @Test
    @Transactional
    public void createCustomerHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerHostRepository.findAll().size();

        // Create the CustomerHost with an existing ID
        customerHost.setId(1L);
        CustomerHostDTO customerHostDTO = customerHostMapper.toDto(customerHost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerHostMockMvc.perform(post("/api/customer-hosts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerHostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerHost in the database
        List<CustomerHost> customerHostList = customerHostRepository.findAll();
        assertThat(customerHostList).hasSize(databaseSizeBeforeCreate);

        // Validate the CustomerHost in Elasticsearch
        verify(mockCustomerHostSearchRepository, times(0)).save(customerHost);
    }


    @Test
    @Transactional
    public void getAllCustomerHosts() throws Exception {
        // Initialize the database
        customerHostRepository.saveAndFlush(customerHost);

        // Get all the customerHostList
        restCustomerHostMockMvc.perform(get("/api/customer-hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerHost.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)));
    }
    
    @Test
    @Transactional
    public void getCustomerHost() throws Exception {
        // Initialize the database
        customerHostRepository.saveAndFlush(customerHost);

        // Get the customerHost
        restCustomerHostMockMvc.perform(get("/api/customer-hosts/{id}", customerHost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerHost.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerHost() throws Exception {
        // Get the customerHost
        restCustomerHostMockMvc.perform(get("/api/customer-hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerHost() throws Exception {
        // Initialize the database
        customerHostRepository.saveAndFlush(customerHost);

        int databaseSizeBeforeUpdate = customerHostRepository.findAll().size();

        // Update the customerHost
        CustomerHost updatedCustomerHost = customerHostRepository.findById(customerHost.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerHost are not directly saved in db
        em.detach(updatedCustomerHost);
        updatedCustomerHost
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME);
        CustomerHostDTO customerHostDTO = customerHostMapper.toDto(updatedCustomerHost);

        restCustomerHostMockMvc.perform(put("/api/customer-hosts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerHostDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerHost in the database
        List<CustomerHost> customerHostList = customerHostRepository.findAll();
        assertThat(customerHostList).hasSize(databaseSizeBeforeUpdate);
        CustomerHost testCustomerHost = customerHostList.get(customerHostList.size() - 1);
        assertThat(testCustomerHost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerHost.getSurname()).isEqualTo(UPDATED_SURNAME);

        // Validate the CustomerHost in Elasticsearch
        verify(mockCustomerHostSearchRepository, times(1)).save(testCustomerHost);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerHost() throws Exception {
        int databaseSizeBeforeUpdate = customerHostRepository.findAll().size();

        // Create the CustomerHost
        CustomerHostDTO customerHostDTO = customerHostMapper.toDto(customerHost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerHostMockMvc.perform(put("/api/customer-hosts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerHostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerHost in the database
        List<CustomerHost> customerHostList = customerHostRepository.findAll();
        assertThat(customerHostList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CustomerHost in Elasticsearch
        verify(mockCustomerHostSearchRepository, times(0)).save(customerHost);
    }

    @Test
    @Transactional
    public void deleteCustomerHost() throws Exception {
        // Initialize the database
        customerHostRepository.saveAndFlush(customerHost);

        int databaseSizeBeforeDelete = customerHostRepository.findAll().size();

        // Delete the customerHost
        restCustomerHostMockMvc.perform(delete("/api/customer-hosts/{id}", customerHost.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerHost> customerHostList = customerHostRepository.findAll();
        assertThat(customerHostList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CustomerHost in Elasticsearch
        verify(mockCustomerHostSearchRepository, times(1)).deleteById(customerHost.getId());
    }

    @Test
    @Transactional
    public void searchCustomerHost() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        customerHostRepository.saveAndFlush(customerHost);
        when(mockCustomerHostSearchRepository.search(queryStringQuery("id:" + customerHost.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(customerHost), PageRequest.of(0, 1), 1));

        // Search the customerHost
        restCustomerHostMockMvc.perform(get("/api/_search/customer-hosts?query=id:" + customerHost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerHost.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)));
    }
}

package com.pasqualeb.prima.repository;

import com.pasqualeb.prima.domain.CustomerHost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerHost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerHostRepository extends JpaRepository<CustomerHost, Long> {
}

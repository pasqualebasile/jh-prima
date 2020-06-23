package com.pasqualeb.prima.service.mapper;


import com.pasqualeb.prima.domain.*;
import com.pasqualeb.prima.service.dto.CustomerHostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerHost} and its DTO {@link CustomerHostDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerHostMapper extends EntityMapper<CustomerHostDTO, CustomerHost> {


    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "removeCard", ignore = true)
    CustomerHost toEntity(CustomerHostDTO customerHostDTO);

    default CustomerHost fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerHost customerHost = new CustomerHost();
        customerHost.setId(id);
        return customerHost;
    }
}

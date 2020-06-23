package com.pasqualeb.prima.service.mapper;


import com.pasqualeb.prima.domain.*;
import com.pasqualeb.prima.service.dto.CardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Card} and its DTO {@link CardDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerHostMapper.class})
public interface CardMapper extends EntityMapper<CardDTO, Card> {

    @Mapping(source = "customerHost.id", target = "customerHostId")
    CardDTO toDto(Card card);

    @Mapping(source = "customerHostId", target = "customerHost")
    Card toEntity(CardDTO cardDTO);

    default Card fromId(Long id) {
        if (id == null) {
            return null;
        }
        Card card = new Card();
        card.setId(id);
        return card;
    }
}

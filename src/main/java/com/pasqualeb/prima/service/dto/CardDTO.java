package com.pasqualeb.prima.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pasqualeb.prima.domain.Card} entity.
 */
public class CardDTO implements Serializable {
    
    private Long id;

    private String cardNo;


    private Long customerHostId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getCustomerHostId() {
        return customerHostId;
    }

    public void setCustomerHostId(Long customerHostId) {
        this.customerHostId = customerHostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardDTO)) {
            return false;
        }

        return id != null && id.equals(((CardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardDTO{" +
            "id=" + getId() +
            ", cardNo='" + getCardNo() + "'" +
            ", customerHostId=" + getCustomerHostId() +
            "}";
    }
}

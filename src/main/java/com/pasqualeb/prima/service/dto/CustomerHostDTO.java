package com.pasqualeb.prima.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pasqualeb.prima.domain.CustomerHost} entity.
 */
public class CustomerHostDTO implements Serializable {
    
    private Long id;

    private String name;

    private String surname;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerHostDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerHostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerHostDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            "}";
    }
}

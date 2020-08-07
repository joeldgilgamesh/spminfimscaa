package com.spminfiscaa.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.spminfiscaa.domain.ServPreDetteIntNoStruc} entity.
 */
public class ServPreDetteIntNoStrucDTO implements Serializable {
    
    private Long id;

    private String categorie;

    private LocalDate echeance;

    private LocalDate date;

    private Long total;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServPreDetteIntNoStrucDTO)) {
            return false;
        }

        return id != null && id.equals(((ServPreDetteIntNoStrucDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServPreDetteIntNoStrucDTO{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", echeance='" + getEcheance() + "'" +
            ", date='" + getDate() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
